package com.lab.SpringSecurity_JWT.security;

import com.lab.SpringSecurity_JWT.entity.Operator;
import com.lab.SpringSecurity_JWT.entity.Role;
import com.lab.SpringSecurity_JWT.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsServices implements UserDetailsService {

    private OperatorRepository operatorRepository;

    @Autowired
    private CustomUsersDetailsServices(OperatorRepository operatorRepository){
        this.operatorRepository = operatorRepository;
    }

    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Operator operator = operatorRepository.findByUsername( username ).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new User(operator.getUsername(), operator.getPassword(), mapToAuthorities(operator.getRoles()));
    }
}
