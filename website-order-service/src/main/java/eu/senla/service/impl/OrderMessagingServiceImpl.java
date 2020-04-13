package eu.senla.service.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.repository.OrderRepository;
import eu.senla.controller.OrderController;
import eu.senla.service.OrderMessagingService;
import eu.senla.repository.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class OrderMessagingServiceImpl implements OrderMessagingService {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    public static final String TOPIC_NAME = "order-kitchen";

    public OrderMessagingServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void sendOrder(OrderEvent orderEvent) {
        Order savedOrder = orderRepository.save(orderEvent.getValue());
        orderEvent.setId(savedOrder.getId());
        ListenableFuture<SendResult<String, OrderEvent>> result = kafkaTemplate.send(TOPIC_NAME, orderEvent);
        log.debug("order was send to the kitchen");
    }
}
