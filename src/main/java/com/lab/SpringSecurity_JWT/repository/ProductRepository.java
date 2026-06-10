package com.lab.SpringSecurity_JWT.repository;

import com.lab.SpringSecurity_JWT.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCategoryCode(String code);
    boolean existsByCategoryCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}
