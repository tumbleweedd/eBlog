package com.example.eblog.dto;

import com.example.eblog.enums.Role;
import com.example.eblog.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDTO {
    private String username;
    private Role role;

    public UserRoleDTO(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }

}
