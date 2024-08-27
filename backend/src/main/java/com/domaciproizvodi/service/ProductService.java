package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.ProductNotFoundException;
import com.domaciproizvodi.model.Product;
import com.domaciproizvodi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> searchProducts(String keyword) {
        String normalizedKeyword = normalizeText(keyword);
        String[] keywords = normalizedKeyword.split("\\s+");
        List<Product> results = new ArrayList<>();
        for (String word : keywords) {
            results.addAll(productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(word, word));
        }
        return results;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setImage(updatedProduct.getImage());
                    product.setCategory(updatedProduct.getCategory());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public String normalizeText(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
    }


}
