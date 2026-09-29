package com.toro.backend.application.authentication.login;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.AppRole;

public record LoginResult(
    String accessToken,
    String refreshToken,
    Instant refreshTokenExpiresAt,
    LoginUserResult user
) {

    public record LoginUserResult(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        AppRole role
    ) {

    }

}
