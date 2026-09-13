package com.aryavarta.identity.record.permission;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RolePermissionRequest(

        @NotNull
        UUID roleId,

        @NotNull
        UUID permissionId
) {
}