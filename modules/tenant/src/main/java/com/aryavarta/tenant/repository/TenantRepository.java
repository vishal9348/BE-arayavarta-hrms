package com.aryavarta.tenant.repository;

import com.aryavarta.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    Optional<Tenant> findByCode(String code);

//    Optional<Tenant> findById(UUID id);

    boolean existsByCode(String code);

    boolean existsByNameIgnoreCase(String name);
}