package com.example.eblog.service;

import com.example.eblog.dto.UserForAdminDTO;
import com.example.eblog.dto.UserProfileInfoDTO;
import com.example.eblog.dto.UserRoleDTO;
import com.example.eblog.dto.UserUpdateProfileDTO;
import com.example.eblog.model.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserForAdminDTO> addUser(UserForAdminDTO user);

    ResponseEntity<?> getUserList();

    ResponseEntity<Void> giveRole(UserRoleDTO userRoleDTO);

    UserProfileInfoDTO getUserProfile(String username);

    UserProfileInfoDTO getLoggedUserProfile(UserDetailsImpl loggedUser);

    ResponseEntity<?> deleteUser(String username, UserDetailsImpl loggedUser);

    ResponseEntity<?> updateUser(String username, UserDetailsImpl loggedUser, UserUpdateProfileDTO updateProfileDTO);
}
