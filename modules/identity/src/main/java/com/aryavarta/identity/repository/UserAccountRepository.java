package com.aryavarta.identity.repository;

import com.aryavarta.identity.entity.UserAccount;
import com.aryavarta.identity.entity.enums.UserAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<UserAccount> findByTenantIdAndUsername(UUID tenantId, String username);

    Optional<UserAccount> findByTenantIdAndEmail(UUID tenantId, String email);

    Optional<UserAccount> findByTenantIdAndEmployeeId(UUID tenantId, UUID employeeId);

    List<UserAccount> findAllByTenantId(UUID tenantId);

    List<UserAccount> findAllByTenantIdAndStatus(UUID tenantId, UserAccountStatus status);

    boolean existsByTenantIdAndUsername(UUID tenantId, String username);

    boolean existsByTenantIdAndEmail(UUID tenantId, String email);

    boolean existsByTenantIdAndEmployeeId(UUID tenantId, UUID employeeId);
}