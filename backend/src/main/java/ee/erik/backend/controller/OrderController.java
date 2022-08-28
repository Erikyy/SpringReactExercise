package ee.erik.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.model.ErrorResponse;
import ee.erik.backend.model.Order;
import ee.erik.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/v1/orders")
@Tag(name = "Order", description = "Orders Api")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Operation(summary = "List all orders", description = "Returns all orders with the package.", tags = {"Order"})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @GetMapping(produces = {"application/json"})
    public List<Order> getOrders(@RequestHeader(name = "Accept-Currency", required = false) String currency) {
        return orderService.getOrders(currency);
    }

    @Operation(summary = "Get order by order id", description = "Returns an order by id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Not found. Returs error with status.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public Order getOrder(
        
        @PathVariable Long id, 
        @RequestHeader(name = "Accept-Currency", required = false) String currency
    ) {
        return orderService.getOrder(id, currency);
    }
    
    @Operation(summary = "Add new order", description = "Adds a new order based on what package id was added to database and returns the added order.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Package Not found. Returs error with status.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" }, produces = {"application/json"})
    public Order addNewOrder(
        @RequestBody CreateOrderDto orderDto,
        
        @RequestHeader(name = "Accept-Currency", required = false) String currency
    ) {
        return orderService.addNewOrder(orderDto, currency);
    }
}
