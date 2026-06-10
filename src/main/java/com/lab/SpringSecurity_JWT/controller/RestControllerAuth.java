package com.lab.SpringSecurity_JWT.controller;

import com.lab.SpringSecurity_JWT.dto.AuthResponseDTO;
import com.lab.SpringSecurity_JWT.dto.LoginDTO;
import com.lab.SpringSecurity_JWT.dto.SignUpDTO;
import com.lab.SpringSecurity_JWT.entity.Operator;
import com.lab.SpringSecurity_JWT.entity.Role;
import com.lab.SpringSecurity_JWT.repository.OperatorRepository;
import com.lab.SpringSecurity_JWT.repository.RoleRepository;
import com.lab.SpringSecurity_JWT.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/api/auth")
public class RestControllerAuth {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private OperatorRepository operatorRepository;
    private JwtGenerator jwtGenerator;

    @Autowired

    public RestControllerAuth(PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator,
                              OperatorRepository operatorRepository, RoleRepository roleRepository,
                              AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.operatorRepository = operatorRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDTO signup) {
        if(operatorRepository.existsByUsername(signup.getUsername())){
            return new ResponseEntity<>("Existing user. Please try again", HttpStatus.BAD_REQUEST);
        }

        Operator newOperator = new Operator();
        newOperator.setUsername(signup.getUsername());
        newOperator.setPassword(passwordEncoder.encode(signup.getPassword()));

        Role newRoles = roleRepository.findByName("USER").get();

        newOperator.setRoles(Collections.singletonList(newRoles));
        operatorRepository.save(newOperator);

        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }

    @PostMapping("/adminSignup")
    public ResponseEntity<String> adminSignup(@RequestBody SignUpDTO signUpDTO) {
        if(operatorRepository.existsByUsername(signUpDTO.getUsername())){
            return new ResponseEntity<>("Existing user. Please try again", HttpStatus.BAD_REQUEST);
        }

        Operator newOperator = new Operator();
        newOperator.setUsername(signUpDTO.getUsername());
        newOperator.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role newRoles = roleRepository.findByName("ADMIN").get();

        newOperator.setRoles(Collections.singletonList(newRoles));
        operatorRepository.save(newOperator);

        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    }
