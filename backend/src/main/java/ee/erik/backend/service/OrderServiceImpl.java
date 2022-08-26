package ee.erik.backend.service;

import java.util.List;
import java.util.Optional;

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
import ee.erik.backend.util.CurrencyConverter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PackageRepository packageRepository;

    /**
     * Converts Orders package price
     */
    private Order convertPrice(Order order, String currency) {
        order.getPackageEntity().setPrice(CurrencyConverter.convertTo(order.getPackageEntity().getPrice(), currency));
        return order;
    }
    
    @Override
    public List<Order> getOrders(String currency) {
        return orderRepository.findAll().stream().map(order -> convertPrice(order, currency)).toList();
    }

    @Override
    public Order getOrder(Long id, String currency) {
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        return convertPrice(order.get(), currency);
    }

    @Override
    public Order addNewOrder(CreateOrderDto orderDto, String currency) {
        Optional<PackageEntity> packageEntity = packageRepository.findById(orderDto.getPackageId());
        if (!packageEntity.isPresent()) {
            throw new PackageNotFoundException(orderDto.getPackageId());
        }
        Order order = orderRepository.save(new Order(OrderStatus.NEW, packageEntity.get()));
        return convertPrice(order, currency);
    }
}
