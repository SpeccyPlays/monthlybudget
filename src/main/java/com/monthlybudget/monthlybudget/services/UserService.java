package com.monthlybudget.monthlybudget.services;

import com.monthlybudget.monthlybudget.datatransferobjects.UserDto;
import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.repos.UserRepo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<UserDto> getAllUsers() {
        return this.userRepo.findAllUsersWithoutIncPassword();
    }

    public User findByUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    public boolean saveUser(User user) {
        try {
            user.setUsername(user.getUsername().toLowerCase());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
