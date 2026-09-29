package com.toro.backend.infrastructure.mapper.authentication;

import java.time.Instant;

import com.toro.backend.application.authentication.login.LoginResult;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.authentication.response.LoginResponse;

public final class LoginMapper {

    private LoginMapper() {
    }

    public static LoginResult toResult(
        String accessToken,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        User user,
        OrganizationMember organizationMember
    ) {
        return new LoginResult(
            accessToken,
            refreshToken,
            refreshTokenExpiresAt,
            toUserResult(user, organizationMember)
        );
    }

    public static LoginResult.LoginUserResult toUserResult(
        User user,
        OrganizationMember organizationMember
    ) {
        return new LoginResult.LoginUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            organizationMember == null ? null : organizationMember.getOrganizationRole(),
            organizationMember == null ? null : organizationMember.getOrganization().getId()
        );
    }

    public static LoginResponse toResponse(LoginResult result) {
        return new LoginResponse(
            result.accessToken(),
            result.refreshToken(),
            toUserResponse(result.user())
        );
    }

    private static LoginResponse.LoginUserResponse toUserResponse(LoginResult.LoginUserResult user) {
        return new LoginResponse.LoginUserResponse(
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
