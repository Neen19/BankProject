package ru.sarmosov.customerservice.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sarmosov.customerservice.service.CustomerDetailsService;
import ru.sarmosov.customerservice.util.JWTUtil;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;

    private final String[] EXCLUDE_URLS = {"/api/auth/login"};

    public JWTFilter(JWTUtil jwtUtil, CustomerDetailsService customerDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String requestPath = request.getRequestURI();
        for (String path : EXCLUDE_URLS) {
            if (requestPath.equals(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
        } else {
            String jwtToken = authHeader.replace("Bearer ", "");
            try {
                String phoneNumber = jwtUtil.verifyTokenAndRetrievePhoneNumber(jwtToken);

                UserDetails customerDetails = customerDetailsService.loadUserByUsername(phoneNumber);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        phoneNumber, customerDetails.getPassword(), customerDetails.getAuthorities()
                );

                if (SecurityContextHolder.getContext().getAuthentication() == null)
                    SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
