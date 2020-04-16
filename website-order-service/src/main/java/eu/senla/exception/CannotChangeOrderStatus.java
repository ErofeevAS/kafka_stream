package eu.senla.exception;

import eu.senla.repository.model.enums.OrderStatus;

public class CannotChangeOrderStatus extends RuntimeException {
    private static final String message = "Can not change order status because order status is: ";

    public CannotChangeOrderStatus(OrderStatus status) {
        super(message + status.name());
    }
}
