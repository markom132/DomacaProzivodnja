package com.domaciproizvodi.controller;

import com.domaciproizvodi.dto.CategoryDTO;
import com.domaciproizvodi.dto.mappers.CategoryMapper;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Test CategoryDTO");
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category));
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(categoryService, times(1)).getAllCategories();
        verify(categoryMapper, times(1)).toDTO(category);
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test CategoryDTO", response.getBody().getName());
        verify(categoryService, times(1)).getCategoryById(1L);
        verify(categoryMapper, times(1)).toDTO(category);
    }

    @Test
    void testCreateCategory() {
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryService.createCategory(category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        ResponseEntity<?> response = categoryController.createCategory(categoryDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(categoryDTO, response.getBody());
        verify(categoryService, times(1)).createCategory(category);
        verify(categoryMapper, times(1)).toDTO(category);
    }

    @Test
    void testUpdateCategory() {
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryService.updateCategory(1L, category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        ResponseEntity<?> response = categoryController.updateCategory(1L, categoryDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(categoryService, times(1)).updateCategory(1L, category);
        verify(categoryMapper, times(1)).toDTO(category);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1L);

        ResponseEntity<Void> response = categoryController.deleteCategory(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).deleteCategory(1L);
    }

}
