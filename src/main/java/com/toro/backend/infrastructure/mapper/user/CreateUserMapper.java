package com.toro.backend.infrastructure.mapper.user;

import com.toro.backend.application.user.create_user.CreateUserResult;
import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.user.request.CreateUserRequest;
import com.toro.backend.presentation.user.response.CreateUserResponse;

public final class CreateUserMapper {

    private CreateUserMapper() {
    }

    public static User toModel(CreateUserRequest request, String passwordHash) {
        return User.builder()
            .fullName(request.fullName())
            .email(request.email())
            .phoneNumber(request.phoneNumber())
            .passwordHash(passwordHash)
            .isActive(request.isActive())
            .role(AppRole.USER)
            .build();
    }

    public static CreateUserResult toResult(User user) {
        return new CreateUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getCreatedAt()
        );
    }

    public static CreateUserResponse toResponse(CreateUserResult result) {
        return new CreateUserResponse(
            result.id(),
            result.fullName(),
            result.email(),
            result.phoneNumber(),
            result.isActive(),
            result.createdAt()
        );
    }

}
