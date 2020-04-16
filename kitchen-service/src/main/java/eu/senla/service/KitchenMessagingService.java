package eu.senla.service;

import eu.senla.event.model.OrderEvent;

public interface KitchenMessagingService {
    void notify(OrderEvent orderEvent, String topicName);

    void notify(OrderEvent orderEvent);
}
