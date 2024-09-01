package com.domaciproizvodi.controller;

import com.domaciproizvodi.config.JwtUtil;
import com.domaciproizvodi.dto.OrderDTO;
import com.domaciproizvodi.dto.mappers.OrderMapper;
import com.domaciproizvodi.model.Order;
import com.domaciproizvodi.model.OrderStatus;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private Order order;

    @MockBean
    private OrderDTO orderDTO;

    @InjectMocks
    private OrderController orderController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        User user = new User();
        order = new Order();
        order.setId(1L);
        order.setTotalPrice(new BigDecimal("99.99"));
        order.setOrderStatus(OrderStatus.NOT_CONFIRMED);
        order.setOrderDate(LocalDateTime.parse("2024-08-26 14:00:00", formatter));
        order.setUser(user);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setTotalPrice(new BigDecimal("99.99"));
        orderDTO.setOrderStatus(String.valueOf(OrderStatus.NOT_CONFIRMED));
        orderDTO.setOrderDate(LocalDateTime.parse("2024-08-26 14:00:00", formatter));
        orderDTO.setUserId(1L);

    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));
        when(orderMapper.toDto(order)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetOrderByIdSuccess() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetOrderByIdNotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testCreateOrder() throws Exception {
        when(orderMapper.toEntity(any(OrderDTO.class))).thenReturn(order);

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        when(orderMapper.toDto(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }


    @Test
    @WithMockUser(username = "testuser")
    public void testUpdateOrder() throws Exception {
        when(orderMapper.toEntity(orderDTO)).thenReturn(order);
        when(orderService.updateOrder(1L, order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testConfirmOrder() throws Exception {
        Long orderId = 1L;
        when(orderService.confirmOrder(orderId)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders/confirm/{id}", orderId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}

