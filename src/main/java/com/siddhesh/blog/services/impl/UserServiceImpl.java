package com.siddhesh.blog.services.impl;

import com.siddhesh.blog.domain.entities.User;
import com.siddhesh.blog.repositories.UserRepository;
import com.siddhesh.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getUserById(UUID id){
        return  userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: "+ id));
    }
}
