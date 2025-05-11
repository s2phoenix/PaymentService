package com.example.POCBankService.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final SecretKey secretKey;
    public JwtAuthenticationFilter(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the token from the header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                username = claims.getSubject();
                role = (String) claims.get("role"); // Extract roles from the token
                role = role.replaceAll("[\\[\\]]", "");

                if (role != null && role.startsWith("ROLE_")) {
                    role = role.substring(5);
                }
            } catch (Exception e) {
                logger.error("Invalid or expired JWT token");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // If the token is valid, set the authentication object with the correct role
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
