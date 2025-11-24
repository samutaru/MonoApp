package com.MonoApp.MonoApp.security;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.List;


public class JwtAuthFilter extends GenericFilter {
private final JwtUtil jwtUtil;


public JwtAuthFilter(JwtUtil jwtUtil){ this.jwtUtil = jwtUtil; }


@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
HttpServletRequest req = (HttpServletRequest) request;
String header = req.getHeader("Authorization");
if (StringUtils.hasText(header) && header.startsWith("Bearer ")){
String token = header.substring(7);
if (jwtUtil.validate(token)){
String userId = jwtUtil.getUserIdFromToken(token);
var auth = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
SecurityContextHolder.getContext().setAuthentication(auth);
}
}
chain.doFilter(request, response);
}
}