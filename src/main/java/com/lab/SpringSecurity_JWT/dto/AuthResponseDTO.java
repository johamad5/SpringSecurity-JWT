package com.lab.SpringSecurity_JWT.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessTooken;
    private String tokenType = "Bearer ";
}
