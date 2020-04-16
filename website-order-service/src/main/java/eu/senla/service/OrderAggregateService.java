package eu.senla.service;

import eu.senla.event.model.OrderEvent;
import eu.senla.service.impl.OrderAggregateServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;

public interface OrderAggregateService {
    OrderEvent saveAndNotify(OrderEvent orderEvent);

    void updateAndNotify(Long id, OrderEvent orderEvent);

    @KafkaListener(topicPartitions =
            {@TopicPartition(topic = OrderAggregateServiceImpl.ORDER_KITCHEN_TOPIC, partitions = {"0", "1", "2"})})
    void receiveOrder(@Payload OrderEvent orderEvent) throws InterruptedException;
}
