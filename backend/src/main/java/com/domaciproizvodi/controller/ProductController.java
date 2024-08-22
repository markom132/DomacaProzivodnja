package com.domaciproizvodi.controller;

import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.service.CategoryService;
import com.domaciproizvodi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Category> category = categoryService.getCategoryById(product.getCategory().getId());
        if (!category.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        product.setCategory(category.get());

        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            if (updatedProduct.getCategory() == null || updatedProduct.getCategory().getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Optional<Category> category = categoryService.getCategoryById(updatedProduct.getCategory().getId());
            if (!category.isPresent()) {
                return ResponseEntity.badRequest().body(null);
            }

            updatedProduct.setCategory(category.get());

            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
