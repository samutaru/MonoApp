package com.MonoApp.MonoApp.config;


import com.MonoApp.MonoApp.security.JwtAuthFilter;
import com.MonoApp.MonoApp.security.JwtUtil;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
private final JwtUtil jwtUtil;
public SecurityConfig(JwtUtil jwtUtil){ this.jwtUtil = jwtUtil; }


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
http.csrf().disable()
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
.authorizeHttpRequests().requestMatchers("/api/auth/**", "/api/tips/**").permitAll()
.anyRequest().authenticated();


http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
return http.build();
}
}