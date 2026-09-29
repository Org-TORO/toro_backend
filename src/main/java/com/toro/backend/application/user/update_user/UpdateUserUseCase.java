package com.toro.backend.application.user.update_user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.UserRepository;
import com.toro.backend.infrastructure.mapper.user.UpdateUserMapper;
import com.toro.backend.presentation.user.request.UpdateUserRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UpdateUserValidator updateUserValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UpdateUserResult execute(Long userId, UpdateUserRequest request) {
        User user = updateUserValidator.validate(userId, request);

        UpdateUserMapper.toModel(
            request,
            user,
            passwordEncoder.encode(request.password())
        );

        User savedUser = userRepository.save(user);

        return UpdateUserMapper.toResult(savedUser);
    }

}
