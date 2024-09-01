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

import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.dto.mappers.OrderItemMapper;
import com.domaciproizvodi.exceptions.OrderItemNotFoundException;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

  private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

  @Autowired private OrderItemService orderItemService;

  @Autowired private OrderItemMapper orderItemMapper;

  @GetMapping
  public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
    logger.info("Received request to fetch all order items");
    List<OrderItem> orderItems = orderItemService.getAllOrderItems();
    return ResponseEntity.status(HttpStatus.OK)
        .body(orderItems.stream().map(orderItemMapper::toDTO).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
    logger.info("Received request to fetch order item with id: {}", id);
    OrderItem orderItem =
        orderItemService
            .getOrderItemById(id)
            .orElseThrow(
                () -> {
                  logger.error("Order item not found with id: {}", id);
                  return new OrderItemNotFoundException("Order item with id not found: " + id);
                });
    return ResponseEntity.status(HttpStatus.OK).body(orderItemMapper.toDTO(orderItem));
  }

  @PostMapping
  public ResponseEntity<?> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
    try {
      logger.info("Received request to create new order item");
      OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
      OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(orderItemMapper.toDTO(createdOrderItem));
    } catch (RuntimeException e) {
      logger.error("Error occurred while creating order item: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrderItem(
      @PathVariable Long id, @Valid @RequestBody OrderItemDTO orderItemDTO) {
    try {
      logger.info("Received request to update order item with id: {}", id);
      OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
      OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
      return ResponseEntity.status(HttpStatus.OK).body(orderItemMapper.toDTO(updatedOrderItem));
    } catch (RuntimeException e) {
      logger.error("Error occurred while updating order item with id: {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
    logger.info("Received request to delete order item with id: {}", id);
    orderItemService.deleteOrderItem(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(OrderItemNotFoundException.class)
  public ResponseEntity<String> handleOrderItemNotFoundException(OrderItemNotFoundException e) {
    logger.error("Handling OrderItemNotFoundException: {}", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
