package ee.erik.backend.service;

import java.util.List;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.model.Order;

public interface OrderService {
    /**
     * Gets all orders
     * 
     * @return orders
     */
    List<Order> getOrders(String currency);

    /**
     * Gets order by id
     * 
     * @param id order id
     * @return order by id
     */
    Order getOrder(Long id, String currency);

    /**
     * Adds new order with selected package,
     * when package is not found it will throw PackageNotFoundException
     * 
     * @param orderDto paramater packageId has to be set to existing package id 
     * @return new order
     */
    Order addNewOrder(CreateOrderDto orderDto, String currency);
}
