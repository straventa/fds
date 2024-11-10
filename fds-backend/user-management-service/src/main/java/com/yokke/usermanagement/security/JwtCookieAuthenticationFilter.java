package com.yokke.usermanagement.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yokke.usermanagement.auth.token.TokenValidity;
import com.yokke.usermanagement.auth.token.TokenValidityRepository;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final TokenValidityRepository tokenValidityRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (request.getServletPath().contains("login")
                || request.getServletPath().contains("setup-password")
                || request.getServletPath().contains("forgot-password")
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        if (csrf != null) {
            System.out.println("CSRF Token: " + csrf.getToken());
        }
        response = setSecurityHeaders(response);
        String accessToken = null;
        accessToken = extractTokenFromCookie(request, "access_token");
        if (accessToken == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Please input a access token in the cookie."
            );
        }


        try {
            String userEmail = jwtService.extractUserAccountId(accessToken);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserAccount userDetails = userAccountRepository.findByUserAccountId(userEmail)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                String.format("Unauthorized access: No user account found for email '%s'.", userEmail)));

                TokenValidity tokenValidity = tokenValidityRepository.findById(userEmail)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                String.format("Unauthorized access: No session found in cache for email '%s'.", userEmail)));

                if (!Objects.equals(tokenValidity.getToken(), accessToken)) {
                    log.error("Invalid token for path {}. Expected token: {}, but received: {}",
                            request.getPathInfo(), tokenValidity.getToken(), accessToken);
                    deleteAllCookies(request, response);
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                            "Unauthorized access: Another user may have logged into your account. Please check your login credentials and try again.");
                }

//                if (!Objects.equals(userDetails.getToken(), accessToken)) {
//                    log.error("Invalid token for path {}. Expected token: {}, but received: {}",
//                            request.getPathInfo(), userDetails.getToken(), accessToken);
//                    deleteAllCookies(request, response);
//                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
//                            "Unauthorized access: Another user may have logged into your account. Please check your login credentials and try again.");
//                }
                if (jwtService.isTokenValid(accessToken, userDetails)) {
                    Claims claims = jwtService.extractAllClaims(accessToken);
                    List<String> roles = claims.get("roles", List.class);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception exc) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("message", exc.getMessage());
            new ObjectMapper().writeValue(response.getOutputStream(), error);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Method to delete all cookies
    private void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0); // Set cookie max age to 0 to delete it
                cookie.setValue(null); // Clear the value
                cookie.setPath("/"); // Set the path to the same as the original
                response.addCookie(cookie); // Add the cookie to the response to delete it
            }
        }
    }

    private HttpServletResponse setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "DENY");

        // X-Content-Type-Options
        response.setHeader("X-Content-Type-Options", "nosniff");

        // Strict-Transport-Security
        response.setHeader("Strict-Transport-Security",
                "max-age=31536000; includeSubDomains; preload");

        // Content-Security-Policy
        response.setHeader("Content-Security-Policy",
                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                        "style-src 'self' 'unsafe-inline'; img-src 'self' data: https:; " +
                        "font-src 'self' data: https:; connect-src 'self' https:;");

        // Referrer-Policy
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // Permissions-Policy
        response.setHeader("Permissions-Policy",
                "camera=(), microphone=(), geolocation=()");

        // Cross-Origin headers
        response.setHeader("Cross-Origin-Embedder-Policy", "require-corp");
        response.setHeader("Cross-Origin-Resource-Policy", "same-origin");
        response.setHeader("Cross-Origin-Opener-Policy", "same-origin");
        return response;
    }
}