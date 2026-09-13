package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.Permission;
import com.aryavarta.identity.record.permission.PermissionResponse;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public PermissionResponse toResponse(Permission entity) {
        return new PermissionResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription()
        );
    }
}