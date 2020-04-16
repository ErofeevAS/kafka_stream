package eu.senla.service.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.service.KitchenMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KitchenMessagingServiceImpl implements KitchenMessagingService {
    private static final Logger log = LoggerFactory.getLogger(KitchenMessagingServiceImpl.class);
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String DEFAULT_ORDER_KITCHEN_TOPIC = "order-kitchen";

    public KitchenMessagingServiceImpl(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void notify(OrderEvent orderEvent, String topicName) {
        ListenableFuture<SendResult<String, OrderEvent>> result = kafkaTemplate.send(topicName, orderEvent);
        log.debug("order was send from the kitchen");
    }

    @Override
    public void notify(OrderEvent orderEvent) {
        this.notify(orderEvent, DEFAULT_ORDER_KITCHEN_TOPIC);
    }
}
