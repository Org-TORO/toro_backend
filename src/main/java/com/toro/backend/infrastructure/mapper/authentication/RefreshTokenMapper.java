package com.toro.backend.infrastructure.mapper.authentication;

import java.time.Instant;

import com.toro.backend.application.authentication.refresh_token.RefreshTokenResult;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.authentication.response.RefreshTokenResponse;

public final class RefreshTokenMapper {

    private RefreshTokenMapper() {
    }

    public static RefreshTokenResult toResult(
        String accessToken,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        User user
    ) {
        return new RefreshTokenResult(
            accessToken,
            refreshToken,
            refreshTokenExpiresAt,
            toUserResult(user)
        );
    }

    public static RefreshTokenResult.RefreshTokenUserResult toUserResult(User user) {
        return new RefreshTokenResult.RefreshTokenUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole()
        );
    }

    public static RefreshTokenResponse toResponse(RefreshTokenResult result) {
        return new RefreshTokenResponse(
            result.accessToken(),
            result.refreshToken(),
            toUserResponse(result.user())
        );
    }

    private static RefreshTokenResponse.RefreshTokenUserResponse toUserResponse(
        RefreshTokenResult.RefreshTokenUserResult user
    ) {
        return new RefreshTokenResponse.RefreshTokenUserResponse(
            user.id(),
            user.fullName(),
            user.email(),
            user.phoneNumber(),
            user.role()
        );
    }

}
