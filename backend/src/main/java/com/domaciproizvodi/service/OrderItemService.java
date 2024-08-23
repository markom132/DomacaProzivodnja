package com.domaciproizvodi.service;

import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderItemRepository;
import com.domaciproizvodi.repository.OrderRepository;
import com.domaciproizvodi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {


        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItem.setQuantity(updatedOrderItem.getQuantity());
                    orderItem.setPrice(updatedOrderItem.getPrice());
                    orderItem.setProduct(updatedOrderItem.getProduct());
                    orderItem.setOrder(updatedOrderItem.getOrder());
                    return orderItemRepository.save(orderItem);
                })
                .orElseThrow(() -> new RuntimeException("Order item not found"));
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

}
