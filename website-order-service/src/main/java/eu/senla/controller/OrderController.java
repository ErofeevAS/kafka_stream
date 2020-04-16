package eu.senla.controller;

import eu.senla.event.model.OrderEvent;
import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.repository.model.Order;
import eu.senla.service.OrderAggregateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderAggregateService orderAggregateService;

    public OrderController(OrderAggregateService orderAggregateService) {
        this.orderAggregateService = orderAggregateService;
    }

    @PostMapping
    public Order createOrder(@RequestBody @Valid OrderEvent orderEvent) {
        log.debug("order will be received :{}", orderEvent);
        OrderEvent savedOrder = orderAggregateService.saveAndNotify(orderEvent);
        return savedOrder.getValue();
    }

    @PutMapping(value = "/{id}")
    public void updateOrder(@RequestBody OrderEvent orderEvent, @PathVariable Long id) {
        orderAggregateService.updateAndNotify(id, orderEvent);
    }

    @GetMapping(value = "test")
    public Order testCreationOrder() {
        Order order = new Order();
        order.setPrice(new BigDecimal(22));
        order.setUser_id(123L);
        OrderEvent orderEvent = OrderEvent.builder()
                .value(order)
                .status(OrderEventStatus.NEW)
                .build();
        log.debug("order will be received :{}", orderEvent);
        OrderEvent savedOrder = orderAggregateService.saveAndNotify(orderEvent);
        return savedOrder.getValue();
    }


}
