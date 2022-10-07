package com.example.eblog.model;

import com.example.eblog.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Введите корректный адрес электронной почты")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Поле не должно быть пустым")
    private String username;

    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "Пароль должен иметь минимум 5 символов")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String name;

    @Column(name = "lastname")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String lastname;


    @JsonIgnore
    private boolean isAccountNonLocked;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Album> albums;
}