package eu.senla.service.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.repository.model.Order;
import eu.senla.service.KitchenAggregateService;
import eu.senla.service.KitchenMessagingService;
import eu.senla.service.KitchenService;
import eu.senla.service.job.CheckOrderForFinish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.*;

@Component
public class KitchenAggregateServiceImpl implements KitchenAggregateService {
    private final static Logger log = LoggerFactory.getLogger(KitchenAggregateServiceImpl.class);
    private final KitchenService kitchenService;
    private final KitchenMessagingService kitchenMessagingService;
    private final static short EXECUTION_PERIOD = 500;

    private final List<Future<Order>> runningOrders = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static final String ORDER_KITCHEN_TOPIC = "order-kitchen";

    public KitchenAggregateServiceImpl(KitchenService kitchenService, KitchenMessagingService kitchenMessagingService) {
        this.kitchenService = kitchenService;
        this.kitchenMessagingService = kitchenMessagingService;
    }

    @PostConstruct
    public void init() {
        CheckOrderForFinish checkOrderForFinishJob = new CheckOrderForFinish(runningOrders, kitchenMessagingService);
        scheduledExecutorService.scheduleAtFixedRate(checkOrderForFinishJob, 0, EXECUTION_PERIOD, TimeUnit.MILLISECONDS);
    }

    @Override
    @KafkaListener(topicPartitions =
            {@TopicPartition(topic = ORDER_KITCHEN_TOPIC, partitions = {"0", "1", "2"})})
    public void receiveOrder(@Payload OrderEvent orderEvent) throws InterruptedException {
        log.debug("kitchen received message: {}", orderEvent);
        try {
            OrderEventStatus status = orderEvent.getStatus();
            switch (status) {
                case NEW: {
                    Future<Order> orderFuture = kitchenService.prepareOrder(orderEvent.getValue());
                    runningOrders.add(orderFuture);
                    break;
                }
                case UPDATED:
                default:
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        log.debug("order was finished: {}", orderEvent);
    }

}
