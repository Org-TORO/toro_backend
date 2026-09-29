package com.toro.backend.presentation.authentication.response;

import com.toro.backend.application.authentication.login.LoginResult;
import com.toro.backend.infrastructure.database.enums.AppRole;

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
        AppRole role
    ) {

        public static LoginUserResponse from(LoginResult.LoginUserResult user) {
            return new LoginUserResponse(
                user.id(),
                user.fullName(),
                user.email(),
                user.phoneNumber(),
                user.role()
            );
        }

    }

}
