package ee.erik.backend.unit.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class OrderRepositoryTest {
    
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        //here set packageEntity to null, doesn't matter, the only test below doesn't need PackageEntity
        Order order = new Order(OrderStatus.NEW, null);
        orderRepository.save(order);
    }

    @Test
    public void repositoryShouldReturnOrdersByStatus() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);

        assertThat(orders).isNotEmpty();
    }
}
