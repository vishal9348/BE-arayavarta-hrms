package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.Role;
import com.aryavarta.identity.record.role.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role entity) {
        return new RoleResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getName(),
                entity.getDescription(),
                entity.isSystemFlag(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion()
        );
    }
}