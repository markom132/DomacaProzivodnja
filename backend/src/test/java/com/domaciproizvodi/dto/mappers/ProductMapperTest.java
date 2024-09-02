package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.ProductDTO;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductMapperTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDTO() {
        Category category = new Category();
        category.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("99.00"));
        product.setCategory(category);

        ProductDTO productDTO = productMapper.toDTO(product);

        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getCategory().getId(), productDTO.getCategoryId());
    }

    @Test
    void testToEntity() {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("99.00"));
        productDTO.setCategoryId(1L);

        Product product = productMapper.toEntity(productDTO);

        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getPrice(), product.getPrice());
        assertEquals(productDTO.getCategoryId(), product.getCategory().getId());

        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testToEntity_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productMapper.toEntity(productDTO));

        assertEquals("Category not found", exception.getMessage());
    }

}
