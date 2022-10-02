package com.example.eblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateProfileDTO {
    private String email;
    private String username;
    private String password;
    private String name;
    private String lastname;
}
