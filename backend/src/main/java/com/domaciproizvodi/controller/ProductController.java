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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Received request to fetch all products");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products.stream().map(productMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        logger.info("Received request to fetch product with id: {}", id);
        Product product = productService.getProductById(id)
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDTO(product));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        logger.info("Received request to search products with keyword: {}", keyword);
        List<Product> products = productService.searchProducts(keyword);
        logger.info("Found {} products for keyword: {}", products.size(), keyword);
        return ResponseEntity.status(HttpStatus.OK).body(products.stream().map(productMapper::toDTO).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            logger.info("Received request to create a new product");
            Product product = productMapper.toEntity(productDTO);
            Category category = categoryService.getCategoryById(productDTO.getCategoryId())
                    .orElseThrow(() -> {
                        logger.error("Category not found with id: {}", productDTO.getCategoryId());
                        return new CategoryNotFoundException("Category not found with id: " + productDTO.getCategoryId());
                    });
            product.setCategory(category);

            Product createdProduct = productService.addProduct(product);
            logger.info("Product created successfully with id: {}", createdProduct.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDTO(createdProduct));
        } catch (RuntimeException e) {
            logger.error("Error occurred while creating product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            logger.info("Received request to update product with id: {}", id);
            Product product = productMapper.toEntity(productDTO);
            Category category = categoryService.getCategoryById(productDTO.getCategoryId())
                    .orElseThrow(() -> {
                        logger.error("Category not found with id: {}", productDTO.getCategoryId());
                        return new CategoryNotFoundException("Category not found with id: " + productDTO.getCategoryId());
                    });
            product.setCategory(category);
            Product updatedProduct = productService.updateProduct(id, product);
            logger.info("Product with id: {} updated successfully", id);
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDTO(updatedProduct));
        } catch (RuntimeException e) {
            logger.error("Error occurred while updating product with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Received request to delete product with id: {}", id);
        productService.deleteProduct(id);
        logger.info("Product with id: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        logger.error("Handling ProductNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
        logger.error("Handling CategoryNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
