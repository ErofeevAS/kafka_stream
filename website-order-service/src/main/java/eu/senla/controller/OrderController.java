package eu.senla.controller;

import eu.senla.event.model.OrderEvent;
import eu.senla.service.OrderMessagingService;
import eu.senla.repository.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderMessagingService orderMessagingService;

    public OrderController(OrderMessagingService orderMessagingService) {
        this.orderMessagingService = orderMessagingService;
    }

    @PostMapping
    public void sendOrder(@RequestBody @Valid OrderEvent orderEvent) {
        log.debug("order will be recieved :{}", orderEvent);
        orderMessagingService.sendOrder(orderEvent);
    }
}
