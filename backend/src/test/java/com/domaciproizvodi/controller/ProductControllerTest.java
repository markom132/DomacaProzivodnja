package com.domaciproizvodi.controller;

import com.domaciproizvodi.config.JwtUtil;
import com.domaciproizvodi.dto.ProductDTO;
import com.domaciproizvodi.dto.mappers.ProductMapper;
import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.service.CategoryService;
import com.domaciproizvodi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("9.99"));
        product.setCategory(category);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("9.99"));
        productDTO.setCategoryId(category.getId());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetProductByIdSuccess() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testSearchProducts() throws Exception {
        when(productService.searchProducts("test")).thenReturn(Arrays.asList(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateProductSuccess() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(product.getCategory()));
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productService.addProduct(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateProductCategoryNotFound() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreateProductProductNotFound() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateProductSuccess() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateProductNotFound() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(product.getCategory()));
        when(productService.updateProduct(anyLong(), any(Product.class)))
                .thenThrow(new ProductNotFoundException("Product not found with id: 1"));

        mockMvc.perform(put("/api/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteProductSuccess() throws Exception {
        mockMvc.perform(delete("/api/products/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteProductNotFound() throws Exception {
        doThrow(new ProductNotFoundException("Product not found with id: 1")).when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

}
