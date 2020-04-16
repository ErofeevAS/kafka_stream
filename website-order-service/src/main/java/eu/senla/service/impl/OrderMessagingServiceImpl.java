package eu.senla.service.impl;

import eu.senla.controller.OrderController;
import eu.senla.event.model.OrderEvent;
import eu.senla.service.OrderMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class OrderMessagingServiceImpl implements OrderMessagingService {
    private static final Logger log = LoggerFactory.getLogger(OrderMessagingServiceImpl.class);
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderMessagingServiceImpl(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void notify(OrderEvent orderEvent, String topicName) {
        ListenableFuture<SendResult<String, OrderEvent>> result = kafkaTemplate.send(topicName, orderEvent);
//        log.debug("order was send to the kitchen");
    }

}
