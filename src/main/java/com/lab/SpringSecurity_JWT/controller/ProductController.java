package com.lab.SpringSecurity_JWT.controller;

import com.lab.SpringSecurity_JWT.entity.Product;
import com.lab.SpringSecurity_JWT.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/product")
public class ProductController {
    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @GetMapping("/{categoryCode}")
    public Optional<Product> getByCategoryCode(@PathVariable String categoryCode) {
        try {
            return productRepository.findByCategoryCode(categoryCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
