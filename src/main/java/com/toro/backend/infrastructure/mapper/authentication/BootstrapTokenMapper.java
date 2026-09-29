package com.toro.backend.infrastructure.mapper.authentication;

import java.time.Instant;

import com.toro.backend.application.authentication.bootstrap_token.BootstrapTokenResult;
import com.toro.backend.presentation.authentication.response.BootstrapTokenResponse;

public final class BootstrapTokenMapper {

    private BootstrapTokenMapper() {
    }

    public static BootstrapTokenResult toResult(
        String accessToken,
        String refreshToken,
        Instant refreshTokenExpiresAt
    ) {
        return new BootstrapTokenResult(
            accessToken,
            refreshToken,
            refreshTokenExpiresAt
        );
    }

    public static BootstrapTokenResponse toResponse(BootstrapTokenResult result) {
        return new BootstrapTokenResponse(result.accessToken());
    }

}
