package com.example.eblog.service.impl;

import com.example.eblog.dto.UserForAdminDTO;
import com.example.eblog.dto.UserProfileInfoDTO;
import com.example.eblog.dto.UserRoleDTO;
import com.example.eblog.dto.UserUpdateProfileDTO;
import com.example.eblog.enums.Role;
import com.example.eblog.exception.BadRequestExceptionThrower;
import com.example.eblog.exception.UserNotFound;
import com.example.eblog.model.User;
import com.example.eblog.model.UserDetailsImpl;
import com.example.eblog.repository.UserRepository;
import com.example.eblog.service.UserService;
import com.example.eblog.util.EmptyJsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<UserForAdminDTO> addUser(UserForAdminDTO user) {
        if (userRepository.findByUsernameInContainer(user.getUsername()).isPresent()) {
            BadRequestExceptionThrower.throwUserExistsException();
        } else if (userRepository.findUserByEmailInContainer(user.getEmail()).isPresent()) {
            BadRequestExceptionThrower.throwUserExistsException();
        }

        User newUSer = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .role(user.getRole())
                .isAccountNonLocked(user.isAccountNonLocked())
                .build();
        return new ResponseEntity<>(new UserForAdminDTO(newUSer), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> giveRole(UserRoleDTO userRoleDTO) {
        User user = userRepository.findByUsernameInContainer(userRoleDTO.getUsername())
                .orElseThrow(UserNotFound::new);
        Role giveRole = userRoleDTO.getRole();

        if (giveRole != Role.USER) {
            if (giveRole == user.getRole()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            user.setRole(giveRole);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserProfileInfoDTO getUserProfile(String username) {
        User user = userRepository.findUserByUsername(username);

        return new UserProfileInfoDTO(user.getUsername(), user.getName(),
                user.getLastname(), user.getEmail());
    }

    @Override
    public UserProfileInfoDTO getLoggedUserProfile(UserDetailsImpl loggedUser) {
        User user = userRepository.findUserByUsername(loggedUser.getUsername());

        return new UserProfileInfoDTO(user.getUsername(), user.getName(),
                user.getLastname(), user.getEmail());
    }

    @Override
    public ResponseEntity<?> deleteUser(String username, UserDetailsImpl loggedUser) {
        if (userRepository.findByUsernameInContainer(username).isPresent()) {
            for (GrantedAuthority authority : loggedUser.getAuthorities()) {
                if (authority.toString().equals(Role.ADMIN.name()) || loggedUser.getUsername().equals(username)) {
                    userRepository.delete(userRepository.findUserByUsername(username));
                    Map<String, String> deleteUserMapInfo = new LinkedHashMap<>();
                    deleteUserMapInfo.put("username", username);
                    deleteUserMapInfo.put("status", "Deleted successfully!");
                    return new ResponseEntity<>(deleteUserMapInfo, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(String username,
                                        UserDetailsImpl loggedUser,
                                        UserUpdateProfileDTO updateProfileDTO) {
        if (userRepository.findByUsernameInContainer(username).isPresent()) {
            User currentUser = userRepository.findUserByUsername(username);
            for (GrantedAuthority authority : loggedUser.getAuthorities()) {
                if (authority.toString().equals(Role.ADMIN.name()) || loggedUser.getUsername().equals(username)) {
                    User newUSer = userRepository.findUserByUsername(username);

                    newUSer.setUsername(updateProfileDTO.getUsername());
                    newUSer.setEmail(updateProfileDTO.getEmail());
                    newUSer.setName(updateProfileDTO.getName());
                    newUSer.setLastname(updateProfileDTO.getLastname());
                    newUSer.setPassword(passwordEncoder.encode(updateProfileDTO.getPassword()));
                    newUSer.setRole(Role.USER);
                    newUSer.setAccountNonLocked(true);
                    userRepository.save(newUSer);

                    LinkedHashMap<String, String> mapInfo = new LinkedHashMap<>();
                    mapInfo.put("email", currentUser.getEmail());
                    mapInfo.put("status", "Profile has been updated successfully");
                    return new ResponseEntity<>(mapInfo, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getUserList() {
        List<User> authUserList = userRepository.findAll();

        if (!authUserList.isEmpty()) {
            return new ResponseEntity<>(userRepository.findAll().stream().map(UserForAdminDTO::new).collect(Collectors.toList()),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}


