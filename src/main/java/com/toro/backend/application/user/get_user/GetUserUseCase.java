package com.toro.backend.application.user.get_user;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.mapper.user.GetUserMapper;

import lombok.RequiredArgsConstructor;


@Service 
@RequiredArgsConstructor 
public class GetUserUseCase {

    private final GetUserValidator getUserValidator;


    public GetUserResult execute(Long userId) {

        User user = getUserValidator.validate(userId);

        return GetUserMapper.toResult(user);
    }

}
