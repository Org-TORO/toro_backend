package com.toro.backend.infrastructure.mapper.user;

import com.toro.backend.application.user.get_users.GetUsersResult;
import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.presentation.user.response.GetUsersResponse;

public final class GetUsersMapper {

    private GetUsersMapper() {
    }

    public static GetUsersResult toResult(User user) {
        return new GetUsersResult(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.isActive(),
            user.getCreatedAt()
        );
    }

    public static GetUsersResponse toResponse(GetUsersResult result) {
        return new GetUsersResponse(
            result.id(),
            result.fullName(),
            result.email(),
            result.phoneNumber(),
            result.isActive(),
            result.createdAt()
        );
    }

}
