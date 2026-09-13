package com.aryavarta.identity.mapper;

import com.aryavarta.identity.entity.UserAccount;
import com.aryavarta.identity.record.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserResponse toResponse(UserAccount entity) {
        return new UserResponse(
                entity.getId(),
                entity.getTenantId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getAuthProvider(),
                entity.getEmployeeId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion()
        );
    }
}