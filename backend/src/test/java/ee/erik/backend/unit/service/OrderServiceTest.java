package ee.erik.backend.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.exception.OrderNotFoundException;
import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.OrderRepository;
import ee.erik.backend.repository.PackageRepository;
import ee.erik.backend.service.OrderService;
import ee.erik.backend.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl();

    private Order testOrder;

    private PackageEntity testPackageEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        //here descriptions are not needed
        testPackageEntity = new PackageEntity(PackageType.PREMIUM, 9.99, new PackageCategory());
        testPackageEntity.setId(1L);
        testOrder = new Order(OrderStatus.NEW, testPackageEntity);
        testOrder.setId(1L);

    }
    
    @Test
    public void serviceShouldReturnAllOrders() {
        given(orderRepository.findAll()).willReturn(List.of(testOrder));

        List<Order> testOrders = orderService.getOrders("eur");
        
        assertThat(testOrders).isNotEmpty();
        assertThat(testOrders).contains(testOrder);
    }

    @Test
    public void serviceShouldGetOrderById() {
        given(orderRepository.findById(1L)).willReturn(Optional.of(testOrder));

        Order given = orderService.getOrder(1L, null);
        
        assertEquals(given, testOrder);
    }

    @Test
    public void serviceShouldThrowExceptionWhenGettingNonExistantOrder() {
        given(orderRepository.findById(2L)).willReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrder(2L, null);
        });
    }

    @Test
    public void serviceShouldAddNewOrder() {
        given(packageRepository.findById(1L)).willReturn(Optional.of(testPackageEntity));
        given(orderRepository.save(Mockito.any(Order.class))).willReturn(testOrder);
        
        Order order = orderService.addNewOrder(new CreateOrderDto(1L), null);

        assertEquals(order, testOrder);
    }

    @Test
    public void serviceShouldThrowExceptionWhenPackageNotFoundWhileAddingNewOrder() {
        assertThrows(PackageNotFoundException.class, () -> {
            orderService.addNewOrder(new CreateOrderDto(2L), null);
        });
    }
    
}
