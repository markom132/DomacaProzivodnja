package com.domaciproizvodi.service;

import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderItemRepository;
import com.domaciproizvodi.repository.OrderRepository;
import com.domaciproizvodi.repository.ProductRepository;
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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Proizvod nije pronađen: " + item.getProduct().getId()));
            item.setProduct(product);
            item.setOrder(order);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {

                    for (OrderItem item : order.getItems()) {
                        orderItemService.deleteOrderItem(item.getId());
                    }
                    order.getItems().clear();

                    order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setTotalPrice(updatedOrder.getTotalPrice());
                    order.setOrderStatus(updatedOrder.getOrderStatus());

                    for (OrderItem item : updatedOrder.getItems()) {
                        Product product = productRepository.findById(item.getProduct().getId())
                                .orElseThrow(() -> new RuntimeException("Proizvod nije pronađen: " + item.getProduct().getId()));

                        item.setProduct(product);
                        item.setOrder(order);
                        order.getItems().add(item);
                    }

                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
