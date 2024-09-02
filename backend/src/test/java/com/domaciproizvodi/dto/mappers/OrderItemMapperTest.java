package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.OrderRepository;
import com.domaciproizvodi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderItemMapperTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderItemMapper orderItemMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDTO() {
        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("19.99"));

        OrderItemDTO orderItemDTO = orderItemMapper.toDTO(orderItem);

        assertEquals(1L, orderItemDTO.getId());
        assertEquals(1L, orderItemDTO.getProductId());
        assertEquals(1L, orderItemDTO.getOrderId());
        assertEquals(2L, orderItemDTO.getQuantity());
        assertEquals(new BigDecimal("19.99"), orderItemDTO.getPrice());
    }

    @Test
    void testToEntity() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(1L);
        orderItemDTO.setProductId(1L);
        orderItemDTO.setOrderId(1L);
        orderItemDTO.setQuantity(2);
        orderItemDTO.setPrice(new BigDecimal("19.99"));

        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);

        assertEquals(1L, orderItem.getId());
        assertEquals(1L, orderItem.getProduct().getId());
        assertEquals(1L, orderItem.getOrder().getId());
        assertEquals(2L, orderItem.getQuantity());
        assertEquals(new BigDecimal("19.99"), orderItem.getPrice());

        verify(productRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).findById(anyLong());
    }

}
