package com.toro.backend.application.user.get_users;

import java.util.List;

import org.springframework.stereotype.Service;

import com.toro.backend.infrastructure.database.enums.AppRole;
import com.toro.backend.infrastructure.database.repository.UserRepository;
import com.toro.backend.infrastructure.mapper.user.GetUsersMapper;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class GetUsersUseCase {

    private final UserRepository userRepository;

    public List<GetUsersResult> execute() {
        return userRepository.findByRoleNot(AppRole.ADMIN)
            .stream()
            .map(GetUsersMapper::toResult)
            .toList();
    }

}
