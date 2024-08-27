package com.domaciproizvodi.controller;

import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.dto.mappers.OrderItemMapper;
import com.domaciproizvodi.exceptions.OrderItemNotFOundException;
import com.domaciproizvodi.exceptions.OrderNotFoundException;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.status(HttpStatus.OK).body(orderItems.stream().map(orderItemMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id)
                .orElseThrow(() -> new OrderItemNotFOundException("Order item with not found with id: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(orderItemMapper.toDTO(orderItem));
    }

    @PostMapping
    public ResponseEntity<?> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
        try {
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
            OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemMapper.toDTO(createdOrderItem));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItemDTO orderItemDTO) {
        try {
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
            return ResponseEntity.status(HttpStatus.OK).body(orderItemMapper.toDTO(updatedOrderItem));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
