package eu.senla.service.impl;

import eu.senla.event.model.OrderEvent;
import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.exception.CannotChangeOrderStatus;
import eu.senla.repository.OrderRepository;
import eu.senla.repository.model.Order;
import eu.senla.repository.model.enums.OrderStatus;
import eu.senla.service.OrderMessagingService;
import eu.senla.service.OrderService;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order with id" + id + " not found"));
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderById(Long id, Order order) {
        Order updatedOrder = null;
        Order foundOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order with id" + id + " not found"));
        OrderStatus status = foundOrder.getStatus();
        if (status == OrderStatus.PENDING || status == OrderStatus.NEW) {
            updateOrder(foundOrder, order);
            updatedOrder = orderRepository.save(foundOrder);
            OrderEvent orderEvent = OrderEvent.builder()
                    .id(updatedOrder.getId())
                    .status(OrderEventStatus.UPDATED)
                    .value(updatedOrder)
                    .build();
//            ListenableFuture<SendResult<String, OrderEvent>> result = kafkaTemplate.send(TOPIC_NAME, orderEvent);
        } else {
            throw new CannotChangeOrderStatus(status);
        }
        return updatedOrder;
    }

    private void updateOrder(Order foundOrder, Order order) {
//        foundOrder.setItems(order.getItems());
        foundOrder.setItems(Arrays.asList("UPDATED ITEM1", "UPDATED ITEM2"));
        foundOrder.setStatus(OrderStatus.UPDATED);
    }
}
