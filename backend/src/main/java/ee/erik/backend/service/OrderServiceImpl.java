package ee.erik.backend.service;

import java.util.List;
import java.util.Optional;

import ee.erik.backend.util.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.exception.OrderNotFoundException;
import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.repository.OrderRepository;
import ee.erik.backend.repository.PackageRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PackageRepository packageRepository;

    /**
     * Converts Orders package price
     */

    
    @Override
    public List<Order> getOrders(String currency) {
        return orderRepository.findAll().stream().peek(order -> UtilFunctions.convertPrice(order.getPackageEntity(), currency)).toList();
    }

    @Override
    public Order getOrder(Long id, String currency) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException(id);
        }
        UtilFunctions.convertPrice(order.get().getPackageEntity(), currency);
        return order.get();
    }

    @Override
    public Order addNewOrder(CreateOrderDto orderDto, String currency) {
        Optional<PackageEntity> packageEntity = packageRepository.findById(orderDto.getPackageId());
        if (packageEntity.isEmpty()) {
            throw new PackageNotFoundException(orderDto.getPackageId());
        }
        Order order = orderRepository.save(new Order(OrderStatus.NEW, packageEntity.get()));
        UtilFunctions.convertPrice(order.getPackageEntity(), currency);
        return order;
    }
}
