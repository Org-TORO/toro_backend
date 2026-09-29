package com.toro.backend.application.authentication.refresh_token;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.enums.RevokedReason;
import com.toro.backend.infrastructure.database.models.LoginSession;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.LoginSessionRepository;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.exception.InvalidRefreshTokenException;
import com.toro.backend.infrastructure.mapper.authentication.RefreshTokenMapper;
import com.toro.backend.infrastructure.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final RefreshTokenValidator refreshTokenValidator;
    private final LoginSessionRepository loginSessionRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final JwtService jwtService;


    /*
        CASE 1: Refresh token is INVALID => Throw new Business Validation Exception (Since this refresh token is not issued || not existed || not owned have owned user in backend)
        CASE 2: Refresh token is EXPIRED => Revoke refresh token as SESSION_EXPIRED and throw new Invalid Refresh Token Exception
        CASE 3: Refresh token is VALID and NOT EXPIRED => Generate new access token AND refresh token
    */
   
    public RefreshTokenResult execute(String refreshToken) {

        // CASE 1
        LoginSession currentLoginSession = refreshTokenValidator.validate(refreshToken);

        // CASE 2
        if (isExpired(currentLoginSession)) {
            currentLoginSession.setRevokedAt(Instant.now());
            currentLoginSession.setRevokedReason(RevokedReason.SESSION_EXPIRED);

            loginSessionRepository.save(currentLoginSession);

            throw new InvalidRefreshTokenException("Login Session is Expired");
        }

        // CASE 3
        System.out.println("Issue new Access Token AND Refresh Token when Acess Token EXPIRED!!!!");

        Instant now = Instant.now();

        Instant accessTokenExpiresAt =
            jwtService.generateAccessTokenExpiresAt();

        Instant refreshTokenExpiresAt =
            jwtService.generateRefreshTokenExpiresAt();

        User user = currentLoginSession.getUser();

        String newAccessToken =
            jwtService.generateAccessToken(
                user,
                now,
                accessTokenExpiresAt
            );

        String newRefreshToken = jwtService.generateRefreshToken();
        String hashedRefreshToken = jwtService.hashRefreshToken(newRefreshToken);

        // Update previous login session
        currentLoginSession.setHashedRefreshToken(hashedRefreshToken);
        currentLoginSession.setAccessExpiresAt(accessTokenExpiresAt);
        currentLoginSession.setRefreshExpiresAt(refreshTokenExpiresAt);

        loginSessionRepository.save(currentLoginSession);

        OrganizationMember organizationMember = findOrganizationMember(user);

        return RefreshTokenMapper.toResult(
            newAccessToken,
            newRefreshToken,
            refreshTokenExpiresAt,
            user,
            organizationMember
        );
    }


    // PRIVATE METHODS
    private boolean isExpired(LoginSession validLoginSession) {

        Instant refreshExpiresAt = validLoginSession.getRefreshExpiresAt();

        if (!refreshExpiresAt.isAfter(Instant.now())) {
            System.out.println("Login Session expired");
            return true;
        }

        return false;
    }

    private OrganizationMember findOrganizationMember(User user) {
        return organizationMemberRepository
            .findFirstByUserIdOrderByIdAsc(user.getId())
            .orElse(null);
    }

}
