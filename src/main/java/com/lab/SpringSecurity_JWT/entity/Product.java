package com.lab.SpringSecurity_JWT.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_code", unique = true, nullable = false, length = 10)
    @NotBlank
    @Size(min = 6, max = 10)
    private String categoryCode;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotNull
    private BigDecimal price;
}

