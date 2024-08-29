package com.domaciproizvodi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.domaciproizvodi.dto.CategoryDTO;
import com.domaciproizvodi.dto.mappers.CategoryMapper;
import com.domaciproizvodi.exceptions.CategoryNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

  @Autowired private CategoryService categoryService;

  @Autowired private CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    logger.info("Received request to fetch all categories");
    List<Category> categories = categoryService.getAllCategories();
    return ResponseEntity.status(HttpStatus.OK)
        .body(categories.stream().map(categoryMapper::toDTO).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
    logger.info("Received request to fetch category with id: {}", id);
    Category category =
        categoryService
            .getCategoryById(id)
            .orElseThrow(
                () -> {
                  logger.error("Category with id {} not found", id);
                  return new CategoryNotFoundException("Category with id " + id + " not found");
                });
    return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.toDTO(category));
  }

  @PostMapping
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    try {
      logger.info("Received request to create new category with name: {}", categoryDTO.getName());
      Category category = categoryMapper.toEntity(categoryDTO);
      Category createdCategory = categoryService.createCategory(category);
      return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.toDTO(createdCategory));
    } catch (RuntimeException e) {
      logger.error("Error occurred while creating category: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategory(
      @PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
    try {
      logger.info("Received request to update category with id: {}", id);
      Category category = categoryMapper.toEntity(categoryDTO);
      Category updatedCategory = categoryService.updateCategory(id, category);
      logger.info("Successfully updated category with id: {}", id);
      return ResponseEntity.ok(categoryMapper.toDTO(updatedCategory));
    } catch (RuntimeException e) {
      logger.error("Error occurred while updating category with id {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    logger.info("Received request to delete category with id: {}", id);
    categoryService.deleteCategory(id);
    logger.info("Successfully deleted category with id: {}", id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
    logger.error("CategoryNotFoundException: {}", e.getMessage());
    return ResponseEntity.status(404).body(e.getMessage());
  }
}
