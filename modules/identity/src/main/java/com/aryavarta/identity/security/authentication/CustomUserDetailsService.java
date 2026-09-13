package com.aryavarta.identity.security.authentication;

import com.aryavarta.identity.entity.UserAccount;
import com.aryavarta.identity.entity.enums.UserAccountStatus;
import com.aryavarta.identity.repository.PermissionRepository;
import com.aryavarta.identity.repository.RolePermissionRepository;
import com.aryavarta.identity.repository.RoleRepository;
import com.aryavarta.identity.repository.UserAccountRepository;
import com.aryavarta.identity.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    /**
     * Standard Spring Security contract.
     * <p>
     * This method should not be used for tenant-aware authentication because
     * username alone is not sufficient to identify a user in a multi-tenant
     * system.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        throw new UsernameNotFoundException("Tenant context is required to load a user");
    }

    /**
     * Loads a user using both tenant and username.
     */
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByTenantAndUsername(UUID tenantId, String username) {
        UserAccount userAccount = userAccountRepository.findByTenantIdAndUsername(tenantId, username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        List<SimpleGrantedAuthority> authorities = loadAuthorities(tenantId, userAccount.getId());
        return new CustomUserDetails(
                userAccount.getId(),
                userAccount.getTenantId(),
                userAccount.getEmployeeId(),
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccount.getPasswordHash(),
                isEnabled(userAccount),
                true,
                isAccountNonLocked(userAccount),
                true,
                authorities
        );
    }

    private List<SimpleGrantedAuthority> loadAuthorities(UUID tenantId, UUID userId) {

        Set<UUID> roleIds = new HashSet<>();
        userRoleRepository.findAllByTenantIdAndUserId(tenantId, userId).forEach(userRole -> roleIds.add(userRole.getRoleId()));
        if (roleIds.isEmpty()) {
            return List.of();
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (UUID roleId : roleIds) {
            roleRepository.findByIdAndTenantId(roleId, tenantId).ifPresent(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                rolePermissionRepository.findAllByTenantIdAndRoleId(tenantId, roleId).forEach(rolePermission ->
                        permissionRepository.findById(rolePermission.getPermissionId()).ifPresent(permission -> authorities.add(new SimpleGrantedAuthority(permission.getCode()))));
            });
        }
        return new ArrayList<>(authorities);
    }

    private boolean isEnabled(UserAccount userAccount) {
        return userAccount.getStatus() == UserAccountStatus.ACTIVE;
    }

    private boolean isAccountNonLocked(UserAccount userAccount) {
        return userAccount.getStatus() != UserAccountStatus.LOCKED;
    }
}