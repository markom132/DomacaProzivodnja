package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.CategoryNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        logger.info("Fetching category with id {}", id);
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        logger.info("Creating new category with name {}", category.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        logger.info("Updating category with id {}", id);
        return categoryRepository.findById(id)
                .map(category -> {
                    logger.info("Found category with id: {}, updating with new values", id);
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

}
