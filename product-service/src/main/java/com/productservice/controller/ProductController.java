package com.productservice.controller;

import com.productservice.model.Product;
import reactor.core.publisher.Flux;
import com.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    /*@Autowired
    ProductRepository productRepository;

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable Long id) {
        return productRepository.findById(id);
    }
    @GetMapping
    public Flux<Product> getAllProducts(@RequestParam Long userId) {
        return productRepository.findAllByUserId(userId);
    }*/
}
