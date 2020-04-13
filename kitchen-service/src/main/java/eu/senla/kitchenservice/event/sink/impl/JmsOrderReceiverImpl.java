package eu.senla.kitchenservice.event.sink.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.repository.model.Order;
import eu.senla.kitchenservice.event.sink.JmsOrderReceiver;
import eu.senla.kitchenservice.service.KitchenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiverImpl implements JmsOrderReceiver {
    private final static Logger log = LoggerFactory.getLogger(JmsOrderReceiverImpl.class);
    private final KitchenService kitchenService;
    public static final String TOPIC_NAME = "order-kitchen";

    public JmsOrderReceiverImpl(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @Override
    @KafkaListener(topicPartitions =
            {@TopicPartition(topic = TOPIC_NAME, partitions = {"0", "1", "2"})})
    public void receiveOrder(@Payload OrderEvent orderEvent) throws InterruptedException {
        log.debug("kitchen received message: {}", orderEvent);
        kitchenService.prepareOrder(orderEvent.getValue());
        log.debug("order was finished: {}" ,orderEvent);
    }

//    @KafkaListener(topics = TOPIC_NAME, groupId = "test")
//    public String receiveOrder2(@Payload Order message) {
//        log.debug("kitchen received message: {}", message);
//        return message.toString();
//    }
}
