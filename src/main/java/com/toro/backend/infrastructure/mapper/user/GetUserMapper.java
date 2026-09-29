package com.toro.backend.infrastructure.mapper.user;

import com.toro.backend.application.user.get_user.GetUserResult;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.user.response.GetUserResponse;

public final class GetUserMapper {

    private GetUserMapper() {
    }

    public static GetUserResult toResult(User user) {
        return new GetUserResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getCreatedAt()
        );
    }

    public static GetUserResponse toResponse(GetUserResult result) {
        return new GetUserResponse(
            result.id(),
            result.fullName(),
            result.email(),
            result.phoneNumber(),
            result.isActive(),
            result.createdAt()
        );
    }

}
