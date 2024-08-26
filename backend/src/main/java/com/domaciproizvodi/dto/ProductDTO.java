package com.domaciproizvodi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is mandatory")
    @Size(max = 100, message = "Product name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Product description is mandatory")
    private String description;

    @NotNull(message = "Product price is mandatory")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    // @NotNull(message = "Product image is mandatory") //todo enable in finish tests
    private byte[] image;

    @NotNull(message = "Product category is mandatory")
    @Positive(message = "Product category ID must be positive")
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
