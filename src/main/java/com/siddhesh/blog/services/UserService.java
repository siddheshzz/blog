package com.siddhesh.blog.services;

import com.siddhesh.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
