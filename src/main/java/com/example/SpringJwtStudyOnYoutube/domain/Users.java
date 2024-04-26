package com.example.SpringJwtStudyOnYoutube.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String password;

    private String role;

    @Builder
    public Users(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public static Users joinUser(String userName, String password, String role) {
        return Users.builder()
                .userName(userName)
                .password(password)
                .role(role)
                .build();

    }
    public Users() {

    }
}
