package com.domaciproizvodi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long id;

    @NotNull(message = "Order date is mandatory")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDateTime orderDate;

    @NotNull(message = "Order price is mandatory")
    @Positive(message = "Total price must be positive")
    private BigDecimal totalPrice;

    @NotBlank(message = "Order status is mandatory")
    @Size(max = 10, message = "Order status must be less than 10 characters")
    private String orderStatus;

    @Valid
    @Size(min = 1, message = "Order must contain at least one item")
    private List<OrderItemDTO> items;

    @NotNull(message = "User ID is mandatory")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
