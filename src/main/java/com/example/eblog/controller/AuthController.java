package com.example.eblog.controller;

import com.example.eblog.dto.UserRegistrationDTO;
import com.example.eblog.dto.UserSignInDTO;
import com.example.eblog.model.User;
import com.example.eblog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    private ResponseEntity<Map> regNewUser(@Valid @RequestBody User user) {
        return authService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserSignInDTO loginInfo) {
        return authService.signIn(loginInfo);
    }
}
