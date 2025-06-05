package com.example.ppo.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    @Setter(AccessLevel.NONE)
    protected Long id;

    @Column(name = "login", unique = true, nullable = false)
    @Field("login")
    protected String login;

    @Column(nullable = false)
    @Field("password")
    protected String password;

    @Column(nullable = false)
    @Field("tel")
    protected String tel;

    @Column(nullable = false)
    @Field("email")
    protected String email;

    @Column
    @Field("registration")
    @Builder.Default
    protected LocalDateTime registration = LocalDateTime.now();

    @Column
    @Field("fio")
    @Builder.Default
    protected String fio = null;

    @Column(columnDefinition = "TEXT")
    @Field("about")
    @Builder.Default
    protected String about = null;
    
    public boolean isValid() {
        return login != null && !login.isBlank() 
            && tel != null && tel.matches("\\+\\d{11}")
            && password != null && !password.isBlank() && password.length() >= 8
            && email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            && registration != null;
    }
}