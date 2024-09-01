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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
    }

    @Test
    void testGetOrderByIdSuccess() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId().longValue());
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateOrder() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal("100.00"));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(product.getPrice());

        Order order = new Order();
        order.setItems(Arrays.asList(orderItem));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(new BigDecimal("200.00"), createdOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrderProductNotFound() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.getProduct().setId(999L);

        Order order = new Order();
        order.setItems(Arrays.asList(orderItem));

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(order));
    }

    @Test
    void testConfirmOrderSuccess() throws MessagingException {
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.NOT_CONFIRMED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order confirmedOrder = orderService.confirmOrder(1L);

        assertEquals(OrderStatus.CONFIRMED, confirmedOrder.getOrderStatus());
        verify(emailService, times(1)).sendOrderConfirmationEmail(order);
    }

    @Test
    void testConfirmOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.confirmOrder(1L));
    }

    @Test
    void testUpdateOrderSuccess() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal("50.00"));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(product.getPrice());

        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setItems(new ArrayList<>(Arrays.asList(orderItem)));

        Order updatedOrder = new Order();
        updatedOrder.setOrderStatus(OrderStatus.CONFIRMED);
        updatedOrder.setItems(new ArrayList<>(Arrays.asList(orderItem)));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertEquals(new BigDecimal("100.00"), result.getTotalPrice());
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void testUpdateOrderNotFound() {
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(1L, updatedOrder));
    }

    @Test
    void testDeleteOrderSuccess() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrderNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

}
