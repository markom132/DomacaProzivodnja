package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.OrderDTO;
import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.model.*;
import com.domaciproizvodi.repository.ProductRepository;
import com.domaciproizvodi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setItems(order.getItems().stream().map(this::toOrderItemDTO).collect(Collectors.toList()));
        dto.setUserId(order.getUser().getId());
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setTotalPrice(dto.getTotalPrice());
        order.setOrderStatus(OrderStatus.valueOf(dto.getOrderStatus()));
        order.setItems(dto.getItems().stream().map(this::toOrderItemEntity).collect(Collectors.toList()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        return order;
    }

    public OrderItemDTO toOrderItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

    public OrderItem toOrderItemEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        Product product = new Product();
        product.setId(dto.getProductId());

        item.setProduct(product);

        return item;
    }

}
