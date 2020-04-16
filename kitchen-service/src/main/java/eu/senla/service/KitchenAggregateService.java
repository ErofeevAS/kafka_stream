package eu.senla.service;

import eu.senla.event.model.OrderEvent;

public interface KitchenAggregateService {
    void receiveOrder(OrderEvent orderEvent) throws InterruptedException;
}
