package com.example.eblog.dto;

import com.example.eblog.enums.Role;
import com.example.eblog.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForAdminDTO {
    private String username;
    private String email;
    private String name;
    private String lastname;

    public UserForAdminDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastname = user.getLastname();
    }
}
