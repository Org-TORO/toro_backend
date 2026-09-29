package com.toro.backend.infrastructure.mapper.user;

import com.toro.backend.application.user.update_user.UpdateUserResult;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.user.request.UpdateUserRequest;
import com.toro.backend.presentation.user.response.UpdateUserResponse;

public final class UpdateUserMapper {

    private UpdateUserMapper() {
    }

    public static User toModel(UpdateUserRequest request, User user, String passwordHash) {
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setPasswordHash(passwordHash);
        user.setActive(request.isActive());

        return user;
    }

    public static UpdateUserResult toResult(User user) {
        return new UpdateUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getCreatedAt()
        );
    }

    public static UpdateUserResponse toResponse(UpdateUserResult result) {
        return new UpdateUserResponse(
            result.id(),
            result.fullName(),
            result.email(),
            result.phoneNumber(),
            result.isActive(),
            result.createdAt()
        );
    }

}
