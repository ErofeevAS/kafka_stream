package eu.senla.service.impl;

import eu.senla.service.KitchenMessagingService;
import eu.senla.service.KitchenService;
import eu.senla.service.model.Cooking;
import eu.senla.repository.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class KitchenServiceImpl implements KitchenService {
    private final static Logger log = LoggerFactory.getLogger(KitchenServiceImpl.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    @Override
    public Future<Order> prepareOrder(Order order) throws InterruptedException, ExecutionException {
        Cooking cooking = new Cooking(order);
        return executorService.submit(cooking);
    }
}
