package com.example.ppo.user;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(unique = true, nullable = false)
    protected String login;

    @Column(nullable = false)
    protected String password;

    @Column(unique = true)
    protected String tel;
    
    @Column(unique = true)
    protected String email;
    
    @Column
    protected LocalDateTime registration = LocalDateTime.now();
    
    @Column
    protected String fio;

    @Column(columnDefinition = "TEXT")
    protected String about;
    
    @PrePersist
    protected void prePersist() {
        if (this.registration == null) {
            this.registration = LocalDateTime.now();
        }
    }
    
    public boolean isValid() {
        return login != null && !login.isBlank() 
            && tel != null && tel.matches("\\+\\d{11}")
            && password != null && !password.isBlank()
            && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}