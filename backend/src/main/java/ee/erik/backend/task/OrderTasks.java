package ee.erik.backend.task;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.repository.OrderRepository;

@Component
public class OrderTasks {

    private final static Logger log = LoggerFactory.getLogger(OrderTasks.class);

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Sets random status for new orders every 2 minutes
     */
    @Scheduled(cron = "0 0/2 * * * *")
    public void setRandomStatusTask() {
        log.info("Changing New Orders statuses");
        List<Order> newOrders = orderRepository.findByStatus(OrderStatus.NEW);

        for (Order order : newOrders) {
            order.setStatus(new OrderStatus[]{OrderStatus.COMPLETE, OrderStatus.FAILED}[new Random().nextInt(2)]);
        }

        orderRepository.saveAll(newOrders);
    }
}
