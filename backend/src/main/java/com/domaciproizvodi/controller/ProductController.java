package com.domaciproizvodi.controller;

import com.domaciproizvodi.dto.ProductDTO;
import com.domaciproizvodi.dto.mappers.ProductMapper;
import com.domaciproizvodi.exceptions.CategoryNotFoundException;
import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Category;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.service.CategoryService;
import com.domaciproizvodi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity <List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products.stream().map(productMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDTO(product));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = productMapper.toEntity(productDTO);
            Category category = categoryService.getCategoryById(productDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
            product.setCategory(category);

            Product createdProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDTO(createdProduct));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = productMapper.toEntity(productDTO);
            Category category = categoryService.getCategoryById(productDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
            product.setCategory(category);
            Product updatedProduct = productService.updateProduct(id, product);

            return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDTO(updatedProduct));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
