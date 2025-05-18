package org.example.investaccel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    // e.g. roles: “ROLE_USER”, “ROLE_ADMIN”
    private String role;

    public User() { }

    public User(String username, String passwordHash, String role) {
        this.username     = username;
        this.passwordHash = passwordHash;
        this.role         = role;
    }

    // --- Getters & Setters ---
    public Long getId()                   { return id; }
    public String getUsername()           { return username; }
    public void setUsername(String username){ this.username = username; }
    public String getPasswordHash()       { return passwordHash; }
    public void setPasswordHash(String pw) { this.passwordHash = pw; }
    public String getRole()               { return role; }
    public void setRole(String role)      { this.role = role; }
}