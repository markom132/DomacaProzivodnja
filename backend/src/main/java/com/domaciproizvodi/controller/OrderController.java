package com.domaciproizvodi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.domaciproizvodi.dto.OrderDTO;
import com.domaciproizvodi.dto.mappers.OrderMapper;
import com.domaciproizvodi.exceptions.OrderNotFoundException;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired private OrderService orderService;

  @Autowired private OrderMapper orderMapper;

  @GetMapping
  public ResponseEntity<List<OrderDTO>> getOrders() {
    logger.info("Received request to fetch all orders");
    List<Order> orders = orderService.getAllOrders();
    return ResponseEntity.status(HttpStatus.OK)
        .body(orders.stream().map(orderMapper::toDto).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
    logger.info("Received request to fetch order with id: {}", id);
    Order order =
        orderService
            .getOrderById(id)
            .orElseThrow(
                () -> {
                  logger.error("Order not found with id: {}", id);
                  return new OrderNotFoundException("Order not found with id: " + id);
                });
    return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toDto(order));
  }

  @PostMapping
  public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
    try {
      logger.info("Received request to create a new order");
      Order order = orderMapper.toEntity(orderDTO);
      Order createdOrder = orderService.createOrder(order);
      logger.info("Order created successfully with id: {}", createdOrder.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toDto(createdOrder));
    } catch (RuntimeException e) {
      logger.error("Error occurred while creating order: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/confirm/{id}")
  public ResponseEntity<OrderDTO> confirmOrder(@PathVariable Long id) {
    try {
      logger.info("Received request to confirm order with id: {}", id);
      Order confirmedOrder = orderService.confirmOrder(id);
      logger.info("Order with id: {} confirmed successfully", id);
      return ResponseEntity.ok(orderMapper.toDto(confirmedOrder));
    } catch (RuntimeException e) {
      logger.error("Error occurred while confirming order with id: {}", id, e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrder(
      @PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
    try {
      logger.info("Received request to update order with id: {}", id);
      Order order = orderMapper.toEntity(orderDTO);
      Order updatedOrder = orderService.updateOrder(id, order);
      logger.info("Order with id: {} updated successfully", id);
      return ResponseEntity.status(HttpStatus.OK).body(orderMapper.toDto(updatedOrder));
    } catch (RuntimeException e) {
      logger.error("Error occurred while updating order with id: {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    logger.info("Received request to delete order with id: {}", id);
    orderService.deleteOrder(id);
    logger.info("Order with id: {} deleted successfully", id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
    logger.error("Handling OrderNotFoundException: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
