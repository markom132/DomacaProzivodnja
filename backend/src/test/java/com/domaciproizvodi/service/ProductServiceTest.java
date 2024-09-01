package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("9.99"));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> productList = productService.getAllProducts();

        assertNotNull(productList);
        assertEquals(1, productList.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByIdSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(product.getName(), result.get().getName());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(1L);

        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testSearchProducts() {
        when(productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Arrays.asList(product));

        List<Product> result = productService.searchProducts("test");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(productRepository, times(1)).findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void testAddProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.addProduct(product);

        assertNotNull(createdProduct);
        assertEquals(product.getName(), createdProduct.getName());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(1L, product);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, product));

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProductSuccess() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void testNormalizeText() {
        String input = "šolja";
        String expected = "solja";

        String result = productService.normalizeText(input);

        assertEquals(expected, result);
    }

}
