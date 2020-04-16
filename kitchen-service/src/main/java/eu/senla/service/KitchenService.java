package eu.senla.service;

import eu.senla.repository.model.Order;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface KitchenService {
    Future<Order> prepareOrder(Order order) throws InterruptedException, ExecutionException;
}
