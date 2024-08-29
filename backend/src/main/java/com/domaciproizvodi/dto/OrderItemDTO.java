package com.domaciproizvodi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class OrderItemDTO {

  private Long id;

  @NotNull(message = "Product ID is mandatory")
  @Positive(message = "Product ID must be a positive number")
  private Long productId;

  private Long orderId;

  @NotNull(message = "Quantity is mandatory")
  @Positive(message = "Quantity must be greater than zero")
  private int quantity;

  @NotNull(message = "Price is mandatory")
  @Positive(message = "Price must be positive")
  private BigDecimal price;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
