package com.domaciproizvodi.controller;

import com.domaciproizvodi.config.JwtUtil;
import com.domaciproizvodi.dto.OrderItemDTO;
import com.domaciproizvodi.dto.mappers.OrderItemMapper;
import com.domaciproizvodi.model.OrderItem;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.service.OrderItemService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemMapper orderItemMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;


    @InjectMocks
    private OrderItemController orderItemController;

    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController).build();

        product = new Product();
        product.setId(1L);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("9.99"));
        orderItem.setProduct(product);

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(1L);
        orderItemDTO.setQuantity(2);
        orderItemDTO.setPrice(new BigDecimal("9.99"));
        orderItemDTO.setProductId(product.getId());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetAllOrderItems() throws Exception {
        List<OrderItem> orderItemList = Arrays.asList(orderItem);
        when(orderItemService.getAllOrderItems()).thenReturn(orderItemList);
        when(orderItemMapper.toDTO(orderItem)).thenReturn(orderItemDTO);

        mockMvc.perform(get("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetOrderItemByIdSuccess() throws Exception {
        when(orderItemService.getOrderItemById(1L)).thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toDTO(orderItem)).thenReturn(orderItemDTO);

        mockMvc.perform(get("/api/order-items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetOrderItemByIdNotFound() throws Exception {
        when(orderItemService.getOrderItemById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/order-items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateOrderItem() throws Exception {
        when(orderItemMapper.toEntity(any(OrderItemDTO.class))).thenReturn(orderItem);
        when(orderItemService.createOrderItem(any(OrderItem.class))).thenReturn(orderItem);
        when(orderItemMapper.toDTO(any(OrderItem.class))).thenReturn(orderItemDTO);

        mockMvc.perform(post("/api/order-items")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderItemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateOrderItem() throws Exception {
        when(orderItemMapper.toEntity(any(OrderItemDTO.class))).thenReturn(orderItem);
        when(orderItemService.updateOrderItem(eq(1L), any(OrderItem.class))).thenReturn(orderItem);
        when(orderItemMapper.toDTO(any(OrderItem.class))).thenReturn(orderItemDTO);

        mockMvc.perform(put("/api/order-items/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteOrderItem() throws Exception {
        mockMvc.perform(delete("/api/order-items/1")
                .with(csrf()))
                .andExpect(status().isNoContent());

        verify(orderItemService, times(1)).deleteOrderItem(1L);
    }

}
