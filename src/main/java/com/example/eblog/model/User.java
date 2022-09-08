package com.example.eblog.model;

import com.example.eblog.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Введите корректный адрес электронной почты")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

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

    @Column(name = "active", nullable = false)
    private int active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
}