package eu.senla.service.model;

import eu.senla.repository.model.Order;
import eu.senla.repository.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cooking implements Callable<Order> {
    private static final Logger log = LoggerFactory.getLogger(Cooking.class);
    private final Random random = new Random();
    private Order order;

    @Override
    public Order call() throws Exception {
        log.debug(" order with id: {} is cooking", order.getId());
        Thread.sleep(200 + random.nextInt(5000));
        order.setStatus(OrderStatus.COMPLETED);
        log.debug(" order with id: {} finished", order.getId());
        return order;
    }
}
