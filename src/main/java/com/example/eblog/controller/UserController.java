package com.example.eblog.controller;

import com.example.eblog.dto.UserForAdminDTO;
import com.example.eblog.dto.UserRoleDTO;
import com.example.eblog.dto.UserUpdateProfileDTO;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserForAdminDTO> addUser(@Valid @RequestBody UserForAdminDTO user) {
        return userService.addUser(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedUserProfile(@AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return new ResponseEntity<>(userService.getLoggedUserProfile(loggedUser), HttpStatus.OK);
    }

    @PutMapping("/giveRole")
    public ResponseEntity<Void> giveRole(@RequestBody UserRoleDTO userRoleDTO) {
        return userService.giveRole(userRoleDTO);
    }

    @GetMapping("/userList")
    public ResponseEntity<?> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserProfile(username), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable String username,
                                               @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        return userService.deleteUser(username, loggedUser);

    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String username,
                                               @AuthenticationPrincipal UserDetailsImpl loggedUser,
                                               @RequestBody UserUpdateProfileDTO updateProfileDTO) {
        return userService.updateUser(username, loggedUser, updateProfileDTO);
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable String username) {
        return userService.getUserPosts(username);
    }



}
