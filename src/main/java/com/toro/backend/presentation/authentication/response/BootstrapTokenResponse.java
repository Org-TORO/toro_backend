package com.toro.backend.presentation.authentication.response;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.enums.OrganizationRole;

public record BootstrapTokenResponse(
    String accessToken,
    String refreshToken,
    BootstrapTokenUserResponse user
) {

    public record BootstrapTokenUserResponse(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        AppRole role,
        Long memberId,
        OrganizationRole organizationRole,
        Long organizationId
    ) {

    }

}
