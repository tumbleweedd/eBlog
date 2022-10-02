package com.example.eblog.dto;

import com.example.eblog.enums.Role;
import com.example.eblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForAdminDTO {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String lastname;
    private Role role;
    private boolean isAccountNonLocked;

    public UserForAdminDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.role = user.getRole();
        this.isAccountNonLocked = user.isAccountNonLocked();
    }
}
