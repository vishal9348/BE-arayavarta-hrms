package com.aryavarta.identity.record.user;

import com.aryavarta.identity.entity.enums.AuthProvider;
import com.aryavarta.identity.entity.enums.UserAccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateUserRequest(

        @NotBlank
        @Size(max = 100)
        String username,

        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(min = 8, max = 200)
        String password,

        UserAccountStatus status,

        AuthProvider authProvider,

        UUID employeeId
) {
}