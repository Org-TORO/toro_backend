package com.toro.backend.application.user.create_user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.models.User;
import com.toro.backend.infrastructure.database.repository.UserRepository;
import com.toro.backend.infrastructure.mapper.user.CreateUserMapper;
import com.toro.backend.presentation.user.request.CreateUserRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CreateUserUseCase {

    private final CreateUserValidator createUserValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public CreateUserResult execute(CreateUserRequest request) {
        createUserValidator.validate(request);

        User user = CreateUserMapper.toModel(
            request,
            passwordEncoder.encode(request.password())
        );

        User savedUser = userRepository.save(user);

        return CreateUserMapper.toResult(savedUser);
    }

}
