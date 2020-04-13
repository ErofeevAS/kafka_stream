package eu.senla.service;

import eu.senla.event.model.OrderEvent;

public interface OrderMessagingService {

    void sendOrder(OrderEvent orderEvent);
}
