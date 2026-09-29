package com.toro.backend.application.authentication.refresh_token;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.models.User;

public record RefreshTokenResult(
    String accessToken,
    String refreshToken,
    Instant refreshTokenExpiresAt,
    RefreshTokenUserResult user
) {

    public record RefreshTokenUserResult(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        AppRole role
    ) {

        public static RefreshTokenUserResult from(User user) {
            return new RefreshTokenUserResult(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
            );
        }

    }

}
