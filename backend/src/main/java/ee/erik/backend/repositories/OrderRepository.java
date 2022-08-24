package ee.erik.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
