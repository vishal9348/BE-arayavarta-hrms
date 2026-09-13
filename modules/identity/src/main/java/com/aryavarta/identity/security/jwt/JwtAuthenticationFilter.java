package com.aryavarta.identity.security.jwt;

import com.aryavarta.identity.security.tenant.TenantSecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = resolveToken(request);

            if (token != null
                    && jwtService.isValid(token)
                    && jwtService.isAccessToken(token)) {

                authenticate(token);
            }

            filterChain.doFilter(request, response);

        } finally {
            TenantSecurityContext.clear();
            SecurityContextHolder.clearContext();
        }
    }

    private void authenticate(String token) {

        var userId = jwtService.getUserId(token);
        var tenantId = jwtService.getTenantId(token);
        var employeeId = jwtService.getEmployeeId(token);
        var username = jwtService.getUsername(token);

        TenantSecurityContext.setTenantId(tenantId);

        List<SimpleGrantedAuthority> authorities =
                new ArrayList<>();

        jwtService.getRoles(token)
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.startsWith("ROLE_")
                                ? role
                                : "ROLE_" + role
                ))
                .forEach(authorities::add);

        jwtService.getPermissions(token)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);

        var principal = new JwtPrincipal(
                userId,
                tenantId,
                employeeId,
                username
        );

        var authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        authorities
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {

        String authorization =
                request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authorization)) {
            return null;
        }

        if (!authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.substring(7).trim();
    }
}