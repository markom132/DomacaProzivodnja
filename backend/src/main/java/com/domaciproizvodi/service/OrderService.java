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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private EmailService emailService;

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        logger.info("Fetching order with id: {}", id);
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        logger.info("Creating new order");
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + item.getProduct().getId()));
            item.setProduct(product);
            item.setOrder(order);
        }
        order.calculateTotalPrice();
        Order savedOrder = orderRepository.save(order);
        logger.info("Order created with id: {}", savedOrder.getId());
        return savedOrder;
    }

    public Order confirmOrder(Long id) {
        logger.info("Confirming order with id: {}", id);
        return orderRepository.findById(id)
                .map(order -> {
                    if (order.getOrderStatus() == OrderStatus.NOT_CONFIRMED) {
                        order.setOrderStatus(OrderStatus.CONFIRMED);
                        orderRepository.save(order);
                        logger.info("Order with id: {} confirmed", id);

                        try {
                            emailService.sendOrderConfirmationEmail(order);
                            logger.info("Order confirmation email sent for order id: {}", id);
                        } catch (MessagingException e) {
                            logger.error("Failed to send order confirmation email for order id: {}", id, e);
                            throw new RuntimeException(e);
                        }
                        return order;
                    } else {
                        logger.warn("Order with id: {} is already confirmed or has another status", id);
                        throw new RuntimeException("Order is already confirmed or has another status.");
                    }
                })
                .orElseThrow(() -> {
                    logger.error("Order not found with id: {}", id);
                    return new RuntimeException("Order not found");
                });
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        logger.info("Updating order with id: {}", id);
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
                    Order savedOrder = orderRepository.save(order);
                    logger.info("Order with id: {} updated successfully", id);
                    return savedOrder;
                })
                .orElseThrow(() -> {
                    logger.error("Order not found with id: {}", id);
                    return new OrderNotFoundException("Order not found with id: " + id);
                });
    }


    public void deleteOrder(Long id) {
        logger.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            logger.error("Order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
        logger.info("Order with id: {} deleted successfully", id);
    }

}
