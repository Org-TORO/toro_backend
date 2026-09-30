package com.toro.backend.application.authentication.bootstrap_token;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.LoginSession;
import com.toro.backend.infrastructure.database.models.OrganizationMember;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.OrganizationMemberRepository;
import com.toro.backend.infrastructure.mapper.authentication.BootstrapTokenMapper;
import com.toro.backend.infrastructure.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class BootstrapTokenUseCase {

    private final BootstrapTokenValidator bootstrapTokenValidator;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final JwtService jwtService;
    
    
    public BootstrapTokenResult execute(String refreshToken) {

        LoginSession currentLoginSession = bootstrapTokenValidator.validate(refreshToken);
        User user = currentLoginSession.getUser();

        Instant currentAccessTokenExpiresAt = currentLoginSession.getAccessExpiresAt();
        String newAccessToken = jwtService.generateAccessToken(
            user, 
            Instant.now(), 
            currentAccessTokenExpiresAt);

        OrganizationMember organizationMember = findOrganizationMember(user);

        return BootstrapTokenMapper.toResult(
            newAccessToken,
            refreshToken,
            currentLoginSession.getRefreshExpiresAt(),
            user,
            organizationMember
        );
    }

    private OrganizationMember findOrganizationMember(User user) {
        return organizationMemberRepository
            .findFirstByUserIdOrderByIdAsc(user.getId())
            .orElse(null);
    }

}
