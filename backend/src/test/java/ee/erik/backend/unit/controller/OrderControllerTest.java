package ee.erik.backend.unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import ee.erik.backend.controller.OrderController;
import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.model.Order;
import ee.erik.backend.model.OrderStatus;
import ee.erik.backend.service.OrderService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    public void setup() {
        testOrder = new Order(OrderStatus.NEW, null);
        testOrder.setId(2L);
    }

    @Test
    public void controllerShouldReturnOrders() throws Exception {
        given(orderService.getOrders(null)).willReturn(List.of(testOrder));
        
        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(testOrder))
                .andDo(print());

    }

    @Test
    public void controllerShouldReturnOrderById() throws Exception {
        given(orderService.getOrder(2L, null)).willReturn(testOrder);

        mockMvc.perform(get("/orders/{id}", 2L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(testOrder))
                .andDo(print());
    }

    @Test
    public void controllerShouldAddNewOrder() throws Exception {
        given(orderService.addNewOrder(new CreateOrderDto(1L), null)).willReturn(testOrder);
        
        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new CreateOrderDto(1L))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(testOrder))
                .andDo(print());
    }
}
