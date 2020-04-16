package eu.senla.service.job;

import eu.senla.event.model.OrderEvent;
import eu.senla.event.model.enums.OrderEventStatus;
import eu.senla.repository.model.Order;
import eu.senla.service.KitchenMessagingService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Future;

public class CheckOrderForFinish implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CheckOrderForFinish.class);
    private final List<Future<Order>> runningOrders;
    private final KitchenMessagingService messagingService;

    public CheckOrderForFinish(List<Future<Order>> runningOrders, KitchenMessagingService messagingService) {
        this.runningOrders = runningOrders;
        this.messagingService = messagingService;
    }

    @SneakyThrows
    @Override
    public void run() {
        if (runningOrders.isEmpty()) {
            return;
        }
        for (Future<Order> runningOrder : runningOrders) {
            boolean isDone = runningOrder.isDone();
            if (isDone) {
                Order order = runningOrder.get();
                OrderEvent orderEvent = OrderEvent.builder()
                        .id(order.getId())
                        .value(order)
                        .status(OrderEventStatus.COMPLETED)
                        .build();
                messagingService.notify(orderEvent);
                runningOrders.remove(runningOrder);
//                log.debug("order :{} is DONE", order.getId());
            }
        }
    }
}
