package com.toro.backend.presentation.authentication.response;

import com.toro.backend.application.authentication.refresh_token.RefreshTokenResult;
import com.toro.backend.infrastructure.database.enums.AppRole;

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
        AppRole role
    ) {

        public static RefreshTokenUserResponse from(RefreshTokenResult.RefreshTokenUserResult user) {
            return new RefreshTokenUserResponse(
                user.id(),
                user.fullName(),
                user.email(),
                user.phoneNumber(),
                user.role()
            );
        }

    }

}
