package eu.senla.kitchenservice.event.sink;

import eu.senla.event.model.OrderEvent;

public interface JmsOrderReceiver {
    void receiveOrder(OrderEvent orderEvent) throws InterruptedException;
}
