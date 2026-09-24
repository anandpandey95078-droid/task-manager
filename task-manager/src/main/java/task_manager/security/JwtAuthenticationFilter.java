package task_manager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Login and Register don't need JWT
        String path = request.getServletPath();

        if (path.equals("/auth/login")
                || path.equals("/auth/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Get Authorization header
        String authHeader =
                request.getHeader("Authorization");

        // If token is not present
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " from token
        String token =
                authHeader.substring(7);

        try {

            // Extract email from JWT
            String email =
                    jwtUtil.extractEmail(token);

            // Create authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.emptyList()
                    );

            // Store authentication in SecurityContext
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            System.out.println("Invalid JWT token");

        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}