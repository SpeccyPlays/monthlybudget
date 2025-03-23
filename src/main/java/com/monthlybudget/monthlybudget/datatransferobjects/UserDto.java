package com.monthlybudget.monthlybudget.datatransferobjects;
import com.monthlybudget.monthlybudget.models.User;

public class UserDto {
    private String username;
    private User.Role role;
    private Long id;

    public UserDto(String username, User.Role role, Long id) {
        this.username = username;
        this.role = role;
        this.id = id;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    public Long getId(){
        return id;
    }
}