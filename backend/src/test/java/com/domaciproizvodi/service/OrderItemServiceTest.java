package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.OrderItemNotFoundException;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    private OrderItem orderItem;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("9.99"));
        orderItem.setProduct(product);
    }

    @Test
    void testGetAllOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        when(orderItemRepository.findAll()).thenReturn(orderItems);

        List<OrderItem> result = orderItemService.getAllOrderItems();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderItem, result.get(0));

        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderItemById() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));

        Optional<OrderItem> result = orderItemService.getOrderItemById(1L);
        assertTrue(result.isPresent());
        assertEquals(orderItem, result.get());

        verify(orderItemRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderItemByIdNotFound() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<OrderItem> result = orderItemService.getOrderItemById(1L);
        assertFalse(result.isPresent());

        verify(orderItemRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateOrderItem() {
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = orderItemService.createOrderItem(orderItem);
        assertNotNull(result);
        assertEquals(orderItem, result);

        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void testUpdateOrderItem() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem updatedOrderItem = new OrderItem();
        updatedOrderItem.setQuantity(3);
        updatedOrderItem.setPrice(new BigDecimal("19.99"));

        OrderItem result = orderItemService.updateOrderItem(1L, updatedOrderItem);
        assertNotNull(result);
        assertEquals(3, result.getQuantity());
        assertEquals(new BigDecimal("19.99"), result.getPrice());

        verify(orderItemRepository, times(1)).findById(1L);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testUpdateOrderItemNotFound() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.empty());

        OrderItem updatedOrderItem = new OrderItem();
        updatedOrderItem.setQuantity(3);
        updatedOrderItem.setPrice(new BigDecimal("19.99"));

        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.updateOrderItem(1L, updatedOrderItem));

        verify(orderItemRepository, times(1)).findById(1L);
        verify(orderItemRepository, never()).save(any(OrderItem.class));
    }

    @Test
    void testDeleteOrderItemSuccess() {
        when(orderItemRepository.existsById(1L)).thenReturn(true);

        orderItemService.deleteOrderItem(1L);

        verify(orderItemRepository, times(1)).existsById(1L);
        verify(orderItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrderItemNotFound() {
        when(orderItemRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.deleteOrderItem(1L));

        verify(orderItemRepository, times(1)).existsById(1L);
        verify(orderItemRepository, never()).deleteById(1L);
    }

}
