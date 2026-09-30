package com.toro.backend.application.authentication.bootstrap_token;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;

public record BootstrapTokenResult(
    String accessToken,
    String refreshToken,
    Instant refreshTokenExpiresAt,
    BootstrapTokenUserResult user
) {

    public record BootstrapTokenUserResult(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        AppRole role,
        OrganizationRole organizationRole,
        Long organizationId
    ) {

    }

}
