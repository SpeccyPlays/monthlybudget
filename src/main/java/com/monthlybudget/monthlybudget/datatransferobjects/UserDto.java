package com.monthlybudget.monthlybudget.datatransferobjects;
import com.monthlybudget.monthlybudget.models.User;

public class UserDto {
    private String username;
    private User.Role role;

    public UserDto(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }
}