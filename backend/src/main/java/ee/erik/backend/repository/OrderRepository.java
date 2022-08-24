package ee.erik.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByStatus(OrderStatus status);
}
