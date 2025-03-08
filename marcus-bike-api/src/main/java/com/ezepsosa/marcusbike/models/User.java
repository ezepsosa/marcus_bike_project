package com.ezepsosa.marcusbike.models;

public class User {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private UserRol rol;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRol getRol() {
        return rol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRol(UserRol rol) {
        this.rol = rol;
    }

    public User() {
    }

    public User(String username, String email, String passwordHash, UserRol rol) {
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    public User(Long id, String username, String email, String passwordHash, UserRol rol) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "AppUser [id=" + id + ", username=" + username + ", email=" + email + ", passwordHash=" + passwordHash
                + ", rol=" + rol + "]";
    }

}
