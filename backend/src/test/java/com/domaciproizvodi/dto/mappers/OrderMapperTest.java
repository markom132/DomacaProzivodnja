package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.OrderDTO;
import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.model.*;
import com.domaciproizvodi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderMapperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderMapper orderMapper;

    private User user;
    private Order order;
    private OrderDTO orderDTO;
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        String time = String.valueOf(LocalDateTime.now());

        user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("19.99"));
        orderItem.setProduct(product);

        order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDateTime.parse(time));
        order.setItems(Arrays.asList(orderItem));
        order.setTotalPrice(new BigDecimal("39.98"));
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setUser(user);

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(1L);
        orderItemDTO.setQuantity(2);
        orderItemDTO.setPrice(new BigDecimal("19.99"));
        orderItemDTO.setProductId(1L);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(LocalDateTime.parse(time));
        orderDTO.setItems(Arrays.asList(orderItemDTO));
        orderDTO.setTotalPrice(new BigDecimal("39.98"));
        orderDTO.setOrderStatus(OrderStatus.CONFIRMED.name());
        orderDTO.setUserId(1L);
    }

    @Test
    void testToDTO() {
        OrderDTO result = orderMapper.toDto(order);

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getOrderDate(), result.getOrderDate());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());
        assertEquals(order.getOrderStatus().name(), result.getOrderStatus());
        assertEquals(order.getUser().getId(), result.getUserId());
        assertEquals(order.getItems().size(), result.getItems().size());
    }

    @Test
    void testToEntity() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Order result = orderMapper.toEntity(orderDTO);

        assertEquals(orderDTO.getId(), result.getId());
        assertEquals(orderDTO.getOrderDate(), result.getOrderDate());
        assertEquals(orderDTO.getTotalPrice(), result.getTotalPrice());
        assertEquals(orderDTO.getOrderStatus(), result.getOrderStatus().name());
        assertEquals(orderDTO.getUserId(), result.getUser().getId());
        assertEquals(orderDTO.getItems().size(), result.getItems().size());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testToOrderItemDTO() {
        OrderItemDTO result = orderMapper.toOrderItemDTO(orderItem);

        assertEquals(orderItemDTO.getId(), result.getId());
        assertEquals(orderItemDTO.getQuantity(), result.getQuantity());
        assertEquals(orderItemDTO.getPrice(), result.getPrice());
        assertEquals(orderItem.getProduct().getId(), result.getProductId());
    }

    @Test
    void testToOrderItemEntity() {
        OrderItem result = orderMapper.toOrderItemEntity(orderItemDTO);

        assertEquals(orderItemDTO.getId(), result.getId());
        assertEquals(orderItemDTO.getQuantity(), result.getQuantity());
        assertEquals(orderItemDTO.getPrice(), result.getPrice());
        assertEquals(orderItemDTO.getProductId(), result.getProduct().getId());
    }

}
