package com.lab.SpringSecurity_JWT.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operator {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_operator")
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    private String username;

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    private String password;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( name = "operator_role", joinColumns =
            @JoinColumn(name = "operator_id", referencedColumnName = "id_operator"),
            inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Role> roles = new ArrayList<>();
}
