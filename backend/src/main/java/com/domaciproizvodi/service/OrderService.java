package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.OrderNotFoundException;
import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.OrderStatus;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderRepository;
import com.domaciproizvodi.repository.ProductRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private EmailService emailService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProduct().getId()));
            item.setProduct(product);
            item.setOrder(order);
        }
        order.calculateTotalPrice();
        return orderRepository.save(order);
    }
    public Order confirmOrder(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    if (order.getOrderStatus() == OrderStatus.NOT_CONFIRMED) {
                        order.setOrderStatus(OrderStatus.CONFIRMED);
                        orderRepository.save(order);
                        // Slanje emaila za potvrdu narudžbine
                        try {
                            emailService.sendOrderConfirmationEmail(order);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                        return order;
                    } else {
                        throw new RuntimeException("Order is already confirmed or has another status.");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {

                    for (OrderItem item : order.getItems()) {
                        orderItemService.deleteOrderItem(item.getId());
                    }
                    order.getItems().clear();

                    order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));

                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setTotalPrice(updatedOrder.getTotalPrice());
                    order.setOrderStatus(updatedOrder.getOrderStatus());

                    for (OrderItem item : updatedOrder.getItems()) {
                        Product product = productRepository.findById(item.getProduct().getId())
                                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProduct().getId()));

                        item.setProduct(product);
                        item.setOrder(order);
                        order.getItems().add(item);
                    }
                    order.calculateTotalPrice();
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }


    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

}
