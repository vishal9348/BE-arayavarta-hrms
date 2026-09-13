package com.aryavarta.tenant.service.impl;

import com.aryavarta.identity.service.IdentityProvisioningService;
import com.aryavarta.identity.record.ProvisionedAdministrator;
import com.aryavarta.tenant.entity.FeatureEntitlement;
import com.aryavarta.tenant.entity.LegalEntity;
import com.aryavarta.tenant.entity.Tenant;
import com.aryavarta.tenant.entity.TenantSetting;
import com.aryavarta.tenant.entity.Plan;
import com.aryavarta.tenant.exception.TenantProvisioningErrorCode;
import com.aryavarta.tenant.exception.TenantProvisioningException;
import com.aryavarta.tenant.mapper.LegalEntityMapper;
import com.aryavarta.tenant.record.CreateTenantRequest;
import com.aryavarta.tenant.record.TenantProvisioningResponse;
import com.aryavarta.tenant.repository.FeatureEntitlementRepository;
import com.aryavarta.tenant.repository.LegalEntityRepository;
import com.aryavarta.tenant.repository.PlanRepository;
import com.aryavarta.tenant.repository.TenantRepository;
import com.aryavarta.tenant.repository.TenantSettingRepository;
import com.aryavarta.tenant.service.TenantProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantProvisioningServiceImpl implements TenantProvisioningService {

    private final TenantRepository tenantRepository;
    private final PlanRepository planRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final TenantSettingRepository tenantSettingRepository;
    private final FeatureEntitlementRepository featureEntitlementRepository;
    private final IdentityProvisioningService identityProvisioningService;
    private final LegalEntityMapper legalEntityMapper;

    @Transactional
    public TenantProvisioningResponse provision(CreateTenantRequest request) {

        String code = request.code().trim().toUpperCase(Locale.ROOT);

        if (tenantRepository.existsByCode(code)) {
            throw new TenantProvisioningException(TenantProvisioningErrorCode.TENANT_CODE_ALREADY_EXISTS, "Tenant code already exists");
        }

        if (tenantRepository.existsByNameIgnoreCase(request.name().trim())) {
            throw new TenantProvisioningException(TenantProvisioningErrorCode.TENANT_NAME_ALREADY_EXISTS, "Tenant name already exists");
        }

        Plan plan = planRepository.findByIdAndActiveTrue(request.planId()).orElseThrow(() -> new TenantProvisioningException(TenantProvisioningErrorCode.PLAN_NOT_FOUND, "Active plan not found"));

        Tenant tenant = Tenant.builder().code(code).name(request.name().trim()).planId(plan.getId()).build();

        Tenant savedTenant = tenantRepository.save(tenant);

        UUID tenantId = savedTenant.getId();

        createDefaultSettings(tenantId, request);

        LegalEntity legalEntity = legalEntityMapper.toEntity(request, tenantId);
        LegalEntity save = legalEntityRepository.save(legalEntity);

        List<FeatureEntitlement> planEntitlements = featureEntitlementRepository.findAllByPlanId(plan.getId());

        copyPlanEntitlements(tenantId, planEntitlements);

        identityProvisioningService.provisionTenantDefaults(tenantId);

        ProvisionedAdministrator administrator = identityProvisioningService.provisionTenantAdministrator(tenantId, request.adminUsername().trim(), request.adminEmail().trim().toLowerCase(Locale.ROOT), request.adminPassword(), request.adminFirstName().trim(), request.adminLastName());

        List<String> enabledFeatures = planEntitlements.stream().map(FeatureEntitlement::getFeatureCode).toList();

        return new TenantProvisioningResponse(tenantId, savedTenant.getCode(), savedTenant.getName(), plan.getId(), plan.getName(), save.getId(), administrator.userId(), administrator.username(), administrator.email(), enabledFeatures, savedTenant.getCreatedAt());
    }

    private void createDefaultSettings(UUID tenantId, CreateTenantRequest request) {

        var settings = request.settings();

        saveSetting(tenantId, "timezone", settings != null && settings.timezone() != null ? settings.timezone() : "Asia/Kolkata");

        saveSetting(tenantId, "locale", settings != null && settings.locale() != null ? settings.locale() : "en-IN");

        saveSetting(tenantId, "currency", settings != null && settings.currency() != null ? settings.currency() : "INR");

        saveSetting(tenantId, "fiscal_year_start", settings != null && settings.fiscalYearStart() != null ? settings.fiscalYearStart() : "04-01");

        saveSetting(tenantId, "date_format", settings != null && settings.dateFormat() != null ? settings.dateFormat() : "dd-MM-yyyy");
    }

    private void saveSetting(UUID tenantId, String key, String value) {
        TenantSetting setting = TenantSetting.builder()
                .tenantId(tenantId)
                .settingKey(key)
                .settingValue(value)
                .dataType("STRING")
                .description(null)
                .encrypted(false)
                .build();
        tenantSettingRepository.save(setting);
    }

    private void copyPlanEntitlements(UUID tenantId, List<FeatureEntitlement> planEntitlements) {

        for (FeatureEntitlement source : planEntitlements) {

            FeatureEntitlement entitlement = FeatureEntitlement.builder().planId(source.getPlanId()).featureCode(source.getFeatureCode()).enabled(source.isEnabled()).build();

            featureEntitlementRepository.save(entitlement);
        }
    }
}
