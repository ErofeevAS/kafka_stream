package eu.senla.service;

import eu.senla.repository.model.Order;

public interface OrderService {

    Order getOrderById(Long id);

    void deleteOrderById(Long id);

    Order save(Order order);

    Order updateOrderById(Long id, Order order);
}
