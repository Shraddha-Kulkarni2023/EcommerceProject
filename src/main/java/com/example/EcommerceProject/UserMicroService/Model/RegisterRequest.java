package com.example.EcommerceProject.UserMicroService.Model;

public class RegisterRequest {

    private String Username;
    private String password;
    private String email;

    private String role;

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String Role) {

        this.role = role;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() { return role; }
}
