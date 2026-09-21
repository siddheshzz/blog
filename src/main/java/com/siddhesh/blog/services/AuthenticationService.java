package com.siddhesh.blog.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
    UserDetails register(String name, String email, String password);
}
