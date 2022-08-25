package ee.erik.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.repository.OrderRepository;
import ee.erik.backend.repository.PackageRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PackageRepository packageRepository;
    
    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
    }
    
    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    public Order addNewOrder(@RequestBody CreateOrderDto orderDto) {
        PackageEntity orderPackage = packageRepository.findById(orderDto.getPackageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Package not found: " + orderDto.getPackageId()));

        return orderRepository.save(new Order(OrderStatus.NEW, orderPackage));
    }
}
