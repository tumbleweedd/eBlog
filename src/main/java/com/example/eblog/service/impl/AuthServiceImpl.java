package com.example.eblog.service.impl;

import com.example.eblog.dto.UserRegistrationDTO;
import com.example.eblog.dto.UserSignInDTO;
import com.example.eblog.enums.Role;
import com.example.eblog.exception.BadRequestExceptionThrower;
import com.example.eblog.model.User;
import com.example.eblog.repository.AuthRepository;
import com.example.eblog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Map> saveUser(User user) {
        if (authRepository.findUserByEmail(user.getEmail()).isPresent()) {
            BadRequestExceptionThrower.throwUserExistsException();
        } else if (authRepository.findUserByUsername(user.getEmail()).isPresent()) {
            BadRequestExceptionThrower.throwUserExistsException();
        }

        User newUser = User.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .isAccountNonLocked(true)
                .build();
        authRepository.save(newUser);
        return new ResponseEntity<>(Map.of("message", "Register done"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> signIn(UserSignInDTO loginInfo) {
        if (authRepository.findUserByUsername(loginInfo.getUsername()).isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        } else if (!Objects.equals(authRepository.findByUsername(loginInfo.getUsername()).getPassword(),
                loginInfo.getPassword())) {
            return new ResponseEntity<>(Map.of("message", "Not correct Data"), HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>(Map.of("message", "welcome, " + loginInfo.getUsername()), HttpStatus.OK);
        }
    }
}
