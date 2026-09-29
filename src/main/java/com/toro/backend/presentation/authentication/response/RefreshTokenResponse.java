package com.toro.backend.presentation.authentication.response;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;

public record RefreshTokenResponse(
    String accessToken,
    String refreshToken,
    RefreshTokenUserResponse user
) {

    public record RefreshTokenUserResponse(
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
