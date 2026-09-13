package com.aryavarta.identity.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!(authentication instanceof TenantUsernamePasswordAuthenticationToken tenantAuthentication)) {

            return null;
        }

        String username = authentication.getName();

        String password = authentication.getCredentials().toString();

        CustomUserDetails userDetails = userDetailsService.loadUserByTenantAndUsername(tenantAuthentication.getTenantId(), username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!userDetails.isEnabled()) {
            throw new BadCredentialsException("User account is inactive");
        }

        if (!userDetails.isAccountNonLocked()) {
            throw new BadCredentialsException("User account is locked");
        }

        return new TenantUsernamePasswordAuthenticationToken(tenantAuthentication.getTenantId(), userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return TenantUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}