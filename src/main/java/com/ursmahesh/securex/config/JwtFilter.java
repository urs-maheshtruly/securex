package com.ursmahesh.securex.config;

import com.ursmahesh.securex.model.Provider;
import com.ursmahesh.securex.service.JWTService;
import com.ursmahesh.securex.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                // Avoid logging sensitive information in production
                // System.out.println("Extracted Username: " + username);
            }
            // Silently fail for missing header - no need to log this for every request
        } catch (Exception e) {
            // Log exception but don't expose details in response
            System.err.println("Error processing JWT token: " + e.getMessage());
            // Continue filter chain without authentication
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                // Get user type from token for additional context information
                String userType = jwtService.extractUserType(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Add user type to authentication details for use in controllers/services
                WebAuthenticationDetails webDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                authToken.setDetails(webDetails);

                SecurityContextHolder.getContext().setAuthentication(authToken);

                // For debugging
                System.out.println("Authenticated user: " + username + " with type: " + userType);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("Request URI: " + path); // Debugging
        return path.equals("/register-user") ||
                path.equals("/login-user") ||
                path.equals("/register-provider") ||
                path.equals("/login-provider") ||
                path.startsWith("/admin") ||                      // ✅ now matches /admin, /admin/vie
                path.startsWith("/available-vehicles");
    }
}