package com.lab.SpringSecurity_JWT.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDTO {
    private String username;

    @Size(min = 10, max = 30)
    private String password;
}
