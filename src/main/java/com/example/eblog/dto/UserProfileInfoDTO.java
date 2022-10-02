package com.example.eblog.dto;

import com.example.eblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileInfoDTO {
    private String username;
    private String name;
    private String lastname;
    private String email;

    public UserProfileInfoDTO(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
}
