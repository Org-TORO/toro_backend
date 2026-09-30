package com.toro.backend.presentation.authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toro.backend.application.authentication.bootstrap_token.BootstrapTokenResult;
import com.toro.backend.application.authentication.bootstrap_token.BootstrapTokenUseCase;
import com.toro.backend.application.authentication.login.LoginResult;
import com.toro.backend.application.authentication.login.LoginUseCase;
import com.toro.backend.application.authentication.refresh_token.RefreshTokenResult;
import com.toro.backend.application.authentication.refresh_token.RefreshTokenUseCase;
import com.toro.backend.infrastructure.api.SuccessResponse;
import com.toro.backend.infrastructure.mapper.authentication.BootstrapTokenMapper;
import com.toro.backend.infrastructure.mapper.authentication.LoginMapper;
import com.toro.backend.infrastructure.mapper.authentication.RefreshTokenMapper;
import com.toro.backend.presentation.authentication.request.BootstrapTokenRequest;
import com.toro.backend.presentation.authentication.request.LoginRequest;
import com.toro.backend.presentation.authentication.request.RefreshTokenRequest;
import com.toro.backend.presentation.authentication.response.BootstrapTokenResponse;
import com.toro.backend.presentation.authentication.response.LoginResponse;
import com.toro.backend.presentation.authentication.response.RefreshTokenResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginUseCase loginUseCase;
    private final BootstrapTokenUseCase bootstrapTokenUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;



    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(
        @Valid @RequestBody LoginRequest request
    ) {

        LoginResult result = loginUseCase.execute(request);
        
        return ResponseEntity
            .ok()
            .body(
                SuccessResponse.success(
                    "Login successfully",
                    LoginMapper.toResponse(result)
                )
            );
    }

    @PostMapping("/bootstrap-token")
    public ResponseEntity<SuccessResponse<BootstrapTokenResponse>> bootstrapToken(
        @Valid @RequestBody BootstrapTokenRequest request
    ) {

        BootstrapTokenResult result = bootstrapTokenUseCase.execute(request.refreshToken());

        return ResponseEntity
            .ok()
            .body(
                SuccessResponse.success(
                    "Bootstrap token successfully",
                    BootstrapTokenMapper.toResponse(result)
                )
            );
    }
    

    @PostMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<RefreshTokenResponse>> refreshToken(
        @Valid @RequestBody RefreshTokenRequest request
    ) {

        RefreshTokenResult result = refreshTokenUseCase.execute(request.refreshToken());
        
        return ResponseEntity
            .ok()
            .body(
                SuccessResponse.success(
                    "Refresh token successfully",
                    RefreshTokenMapper.toResponse(result)
                )
            );
    }

}
