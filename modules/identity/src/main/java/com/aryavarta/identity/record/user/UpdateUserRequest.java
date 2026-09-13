package com.aryavarta.identity.record.user;

import com.aryavarta.identity.entity.enums.UserAccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateUserRequest(

        @Email
        @Size(max = 255)
        String email,

        UserAccountStatus status,

        UUID employeeId
) {
}