package com.toro.backend.application.authentication.login;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.LoginSession;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.LoginSessionRepository;
import com.toro.backend.infrastructure.security.jwt.JwtService;
import com.toro.backend.presentation.authentication.request.LoginRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final LoginValidator validator;
    private final JwtService jwtService;
    private final LoginSessionRepository loginSessionRepository;


    @Transactional
    public LoginResult execute(LoginRequest request) {

        // Business validation
        User user = validator.validate(request);

        Instant accessTokenExpiresAt =
            jwtService.generateAccessTokenExpiresAt();

        Instant refreshTokenExpiresAt =
            jwtService.generateRefreshTokenExpiresAt();

        String accessToken = jwtService.generateAccessToken(
                user,
                Instant.now(),
                accessTokenExpiresAt
        );

        String refreshToken = jwtService.generateRefreshToken();
        String hashedRefreshToken = jwtService.hashRefreshToken(refreshToken);

        // Create once, then rotate the tokens on the existing login session.
        saveLoginSession(user, hashedRefreshToken, accessTokenExpiresAt, refreshTokenExpiresAt);

        
        return new LoginResult(
                accessToken,
                refreshToken,
                refreshTokenExpiresAt,
                LoginResult.LoginUserResult.from(user)
        );
    }


    // PRIVATE METHODS
    private LoginSession saveLoginSession(
            User user,
            String hashedRefreshToken,
            Instant accessExpiresAt,
            Instant refreshExpiresAt
    ) {

        LoginSession loginSession = loginSessionRepository
            .findFirstByUserId(user.getId())
            .orElseGet(() -> {
                LoginSession newSession = new LoginSession();
                
                newSession.setUser(user);

                return newSession;
            });

        loginSession.setHashedRefreshToken(hashedRefreshToken);
        loginSession.setAccessExpiresAt(accessExpiresAt);
        loginSession.setRefreshExpiresAt(refreshExpiresAt);
        loginSession.setRevokedReason(null);
        loginSession.setRevokedAt(null);

        return loginSessionRepository.save(loginSession);
    }

}
