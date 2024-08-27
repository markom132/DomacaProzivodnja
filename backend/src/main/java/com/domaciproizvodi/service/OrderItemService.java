package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.OrderItemNotFOundException;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        logger.info("Fetching all order items");
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(Long id) {
        logger.info("Fetching order item with id: {}", id);
        return orderItemRepository.findById(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        logger.info("Creating new order item for product id: {}", orderItem.getProduct().getId());
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
        logger.info("Updating order item with id: {}", id);
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    logger.info("Found order item with id: {}, updating with new values", id);
                    orderItem.setQuantity(updatedOrderItem.getQuantity());
                    orderItem.setPrice(updatedOrderItem.getPrice());
                    orderItem.setProduct(updatedOrderItem.getProduct());
                    orderItem.setOrder(updatedOrderItem.getOrder());
                    return orderItemRepository.save(orderItem);
                })
                .orElseThrow(() -> {
                    logger.error("Order item not found with id: {}", id);
                    return new OrderItemNotFOundException("Order item not found with id: " + id);
                });
    }

    public void deleteOrderItem(Long id) {
        logger.info("Attempting to delete order item with id: {}", id);
        if (!orderItemRepository.existsById(id)) {
            logger.error("Order item not found with id: {}", id);
            throw new OrderItemNotFOundException("Order item not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
        logger.info("Order item with id: {} deleted successfully", id);
    }

}
