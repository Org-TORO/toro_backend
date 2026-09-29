package com.toro.backend.application.authentication.login;

import java.time.Instant;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.models.User;

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

        public static LoginUserResult from(User user) {
            return new LoginUserResult(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
            );
        }

    }

}
