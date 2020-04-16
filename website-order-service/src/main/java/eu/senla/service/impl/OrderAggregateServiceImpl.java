package eu.senla.service.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.repository.model.Order;
import eu.senla.service.OrderAggregateService;
import eu.senla.service.OrderMessagingService;
import eu.senla.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderAggregateServiceImpl implements OrderAggregateService {
    private final static Logger log = LoggerFactory.getLogger(OrderAggregateServiceImpl.class);
    private final OrderService orderService;
    private final OrderMessagingService orderMessagingService;
    public static final String ORDER_KITCHEN_TOPIC = "order-kitchen";
    public static final String ORDER_DELIVERY_TOPIC = "order-delivery";

    public OrderAggregateServiceImpl(OrderService orderService, OrderMessagingService orderMessagingService) {
        this.orderService = orderService;
        this.orderMessagingService = orderMessagingService;
    }

    @Override
    public OrderEvent saveAndNotify(OrderEvent orderEvent) {
        Order savedOrder = orderService.save(orderEvent.getValue());
        orderEvent.setId(savedOrder.getId());
        orderMessagingService.notify(orderEvent, ORDER_KITCHEN_TOPIC);
        return orderEvent;
    }

    @Override
    public void updateAndNotify(Long id, OrderEvent orderEvent) {
        orderService.updateOrderById(id, orderEvent.getValue());
        orderEvent.setStatus(OrderEventStatus.UPDATED);
        orderMessagingService.notify(orderEvent, ORDER_KITCHEN_TOPIC);
    }

    @Override
    @KafkaListener(topicPartitions =
            {@TopicPartition(topic = ORDER_KITCHEN_TOPIC, partitions = {"0", "1", "2"})})
    public void receiveOrder(@Payload OrderEvent orderEvent) throws InterruptedException {
        log.debug("kitchen received message: {}", orderEvent);
        OrderEventStatus status = orderEvent.getStatus();
        switch (status) {

            case PENDING: {
                log.debug("order {} is preparing", orderEvent.getId());
                break;
            }
            case COMPLETED: {
                orderMessagingService.notify(orderEvent, ORDER_DELIVERY_TOPIC);
                log.debug("order {} was transferred to delivery service ", orderEvent.getId());
                break;
            }
            default:
        }
    }
}
