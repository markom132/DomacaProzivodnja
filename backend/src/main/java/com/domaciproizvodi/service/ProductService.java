package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    public List<Product> searchProducts(String keyword) {
        String normalizedKeyword = normalizeText(keyword);
        logger.info("Searching products with keyword: {}", normalizedKeyword);
        String[] keywords = normalizedKeyword.split("\\s+");
        List<Product> results = new ArrayList<>();
        for (String word : keywords) {
            results.addAll(productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(word, word));
        }
        logger.info("Found {} products for keyword: {}", results.size(), keyword);
        return results;
    }

    public Product addProduct(Product product) {
        logger.info("Adding new product with name: {}", product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        logger.info("Updating product with id: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setImage(updatedProduct.getImage());
                    product.setCategory(updatedProduct.getCategory());
                    Product savedProduct = productRepository.save(product);
                    logger.info("Product with id: {} updated successfully", id);
                    return savedProduct;
                })
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product not found with id: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        logger.info("Product with id: {} deleted successfully", id);
    }

    public String normalizeText(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String result = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
        logger.debug("Normalized text: {} to {}", input, result);
        return result;
    }


}
