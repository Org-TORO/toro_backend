package com.toro.backend.presentation.authentication.response;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    LoginUserResponse user
) {

    public record LoginUserResponse(
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
