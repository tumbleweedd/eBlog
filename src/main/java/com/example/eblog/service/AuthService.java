package com.example.eblog.service;

import com.example.eblog.dto.UserRegistrationDTO;
import com.example.eblog.dto.UserSignInDTO;
import com.example.eblog.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<Map> saveUser(User user);

    ResponseEntity<?> signIn(UserSignInDTO loginInfo);
}
