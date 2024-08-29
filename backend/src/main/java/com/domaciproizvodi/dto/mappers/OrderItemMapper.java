package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderRepository;
import com.domaciproizvodi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

  @Autowired private ProductRepository productRepository;

  @Autowired private OrderRepository orderRepository;

  public OrderItemDTO toDTO(OrderItem orderItem) {
    OrderItemDTO orderItemDto = new OrderItemDTO();
    orderItemDto.setId(orderItem.getId());
    orderItemDto.setProductId(orderItem.getProduct().getId());
    orderItemDto.setOrderId(orderItem.getOrder().getId());
    orderItemDto.setQuantity(orderItem.getQuantity());
    orderItemDto.setPrice(orderItem.getPrice());
    return orderItemDto;
  }

  public OrderItem toEntity(OrderItemDTO orderItemDTO) {
    OrderItem orderItem = new OrderItem();
    orderItem.setId(orderItemDTO.getId());
    orderItem.setQuantity(orderItemDTO.getQuantity());
    orderItem.setPrice(orderItemDTO.getPrice());

    Product product =
        productRepository
            .findById(orderItemDTO.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
    orderItem.setProduct(product);

    Order order =
        orderRepository
            .findById(orderItemDTO.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));
    orderItem.setOrder(order);

    return orderItem;
  }
}
