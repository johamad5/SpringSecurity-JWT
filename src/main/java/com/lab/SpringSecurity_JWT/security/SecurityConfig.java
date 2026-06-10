package com.lab.SpringSecurity_JWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean  // Verifica info de los usuarios que se loguean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean  // Encripta las contraseñas
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean  // Incorpora filtro de seguridad de Jwt creado
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean  // Establece cadena de filtros de seguridad. Se determinan permisos segun roles de acceso
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita proteccion contra CROSS
                .csrf( csrf -> csrf.disable())
                // Permite manejo de excepcione
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Permite la gestión de sesiones
                .sessionManagement( s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Todas las petiociones http deben ser autorizadas
                .authorizeHttpRequests( auth -> auth.requestMatchers("/api/auth/**")
                        .permitAll().anyRequest().authenticated());
                // Filtro básico de http. No necesario para login propio que devuelve JWT. Si se usa para autenticación HTTP Basic
                //.httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
