package com.aryavarta.tenant.repository;

import com.aryavarta.tenant.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlanRepository
        extends JpaRepository<Plan, UUID> {

    Optional<Plan> findByIdAndActiveTrue(UUID id);

    Optional<Plan> findByCode(String code);

    boolean existsByCode(String code);
}