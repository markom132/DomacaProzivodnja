package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.CategoryNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
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
