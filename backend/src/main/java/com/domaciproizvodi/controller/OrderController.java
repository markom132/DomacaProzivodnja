package com.domaciproizvodi.controller;

import com.domaciproizvodi.dto.OrderDTO;
import com.domaciproizvodi.dto.mappers.OrderMapper;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.OrderService;
import com.domaciproizvodi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(value -> ResponseEntity.ok(orderMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderMapper.toEntity(orderDTO);
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(orderMapper.toDto(createdOrder));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderMapper.toEntity(orderDTO);
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(orderMapper.toDto(updatedOrder));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
