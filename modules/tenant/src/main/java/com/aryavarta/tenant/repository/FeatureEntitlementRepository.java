package com.aryavarta.tenant.repository;

import com.aryavarta.tenant.entity.FeatureEntitlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeatureEntitlementRepository extends JpaRepository<FeatureEntitlement, UUID> {

    List<FeatureEntitlement> findAllByPlanId(UUID planId);
}