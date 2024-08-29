package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.CategoryDTO;
import com.domaciproizvodi.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public CategoryDTO toDTO(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setDescription(category.getDescription());
    return categoryDTO;
  }

  public Category toEntity(CategoryDTO categoryDTO) {
    Category category = new Category();
    category.setId(categoryDTO.getId());
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());
    return category;
  }
}
