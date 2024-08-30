package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.CategoryNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        List<Category> mockCategories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
    }

    @Test
    void testGetCategoryById() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        Optional<Category> result = categoryService.getCategoryById(1L);

        assertEquals("Category 1", result.get().getName());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryById(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void createCategory() {
        Category category = new Category();
        category.setName("New Category");

        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals("New Category", result.getName());
    }

    @Test
    void testUpdateCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Old Category");

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.updateCategory(1L, updatedCategory);

        assertEquals("Updated Category", result.getName());
    }

    @Test
    void testUpdateCategoryNotFound() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(1L, updatedCategory));
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCategoryNotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }

}
