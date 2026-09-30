package com.toro.backend.infrastructure.mapper.authentication;

import java.time.Instant;

import com.toro.backend.application.authentication.bootstrap_token.BootstrapTokenResult;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.authentication.response.BootstrapTokenResponse;

public final class BootstrapTokenMapper {

    private BootstrapTokenMapper() {
    }

    public static BootstrapTokenResult toResult(
        String accessToken,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        User user,
        OrganizationMember organizationMember
    ) {
        return new BootstrapTokenResult(
            accessToken,
            refreshToken,
            refreshTokenExpiresAt,
            toUserResult(user, organizationMember)
        );
    }

    public static BootstrapTokenResult.BootstrapTokenUserResult toUserResult(
        User user,
        OrganizationMember organizationMember
    ) {
        return new BootstrapTokenResult.BootstrapTokenUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            organizationMember == null ? null : organizationMember.getOrganizationRole(),
            organizationMember == null ? null : organizationMember.getOrganization().getId()
        );
    }

    public static BootstrapTokenResponse toResponse(BootstrapTokenResult result) {
        return new BootstrapTokenResponse(
            result.accessToken(),
            result.refreshToken(),
            toUserResponse(result.user())
        );
    }

    private static BootstrapTokenResponse.BootstrapTokenUserResponse toUserResponse(
        BootstrapTokenResult.BootstrapTokenUserResult user
    ) {
        return new BootstrapTokenResponse.BootstrapTokenUserResponse(
            user.id(),
            user.fullName(),
            user.email(),
            user.phoneNumber(),
            user.role(),
            user.organizationRole(),
            user.organizationId()
        );
    }

}
