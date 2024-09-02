package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.CategoryDTO;
import com.domaciproizvodi.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryMapperTest {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void testToDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

        assertEquals(1L, categoryDTO.getId());
        assertEquals("Test Category", categoryDTO.getName());
        assertEquals("Test Description", categoryDTO.getDescription());
    }

    @Test
    void testToEntity() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("Test Description");

        Category category = categoryMapper.toEntity(categoryDTO);

        assertEquals(1L, category.getId());
        assertEquals("Test Category", category.getName());
        assertEquals("Test Description", category.getDescription());
    }

}
