package eu.senla.kitchenservice.service.impl;

import eu.senla.repository.model.Order;
import eu.senla.kitchenservice.service.KitchenService;
import eu.senla.kitchenservice.service.model.Cooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class KitchenServiceImpl implements KitchenService {

    private final static Logger log = LoggerFactory.getLogger(KitchenServiceImpl.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void prepareOrder(Order order) {
        Cooking cooking = new Cooking(order);
        Future<Order> submit = executorService.submit(cooking);
    }
}
