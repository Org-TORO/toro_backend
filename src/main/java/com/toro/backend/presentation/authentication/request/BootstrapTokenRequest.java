package com.toro.backend.presentation.authentication.request;

import jakarta.validation.constraints.NotBlank;

public record BootstrapTokenRequest(
    @NotBlank(message = "Refresh token is required")
    String refreshToken
) {

}
