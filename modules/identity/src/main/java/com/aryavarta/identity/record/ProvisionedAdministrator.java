package com.aryavarta.identity.record;

import java.util.UUID;

public record ProvisionedAdministrator(
        UUID userId,
        String username,
        String email
) {
}
