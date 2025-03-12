package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.repos.UserRepo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
public class UserController {

  private final UserRepo userRepo;

  public UserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  
@GetMapping("/admin/users")
  public Iterable<User> findAllEmployees() {
    return this.userRepo.findAll();
  }

@PostMapping("/admin/users") 
  public ResponseEntity<String> saveUser(@RequestBody User user){
    //Check if user already exists
    User exist = userRepo.findByUsername(user.getUsername());
    if (exist != null){
      return ResponseEntity.badRequest().body("Unable to add user");
    }
    
    //Tidy import
    user.setUsername(user.getUsername().toLowerCase());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    userRepo.save(user);
    return ResponseEntity.ok().body("User added");
  }
}