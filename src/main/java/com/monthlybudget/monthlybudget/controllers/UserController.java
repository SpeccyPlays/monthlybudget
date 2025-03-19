package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.datatransferobjects.UserDto;
import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.services.UserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

  @Autowired
  private UserService userService;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)

  public List<UserDto> getAllUsers() {
    return this.userService.getAllUsers();
  }

  @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> saveUser(@RequestBody User user) {

    logger.info("Received POST request: {}", user);
    // Check if user already exists
    User exist = userService.findByUsername(user.getUsername());
    if (exist != null) {
      return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
    }
    if (userService.saveUser(user)) {
      return ResponseEntity.ok().body("{\"message\": \"User added successfully\"}");
    } else {
      return ResponseEntity.badRequest().body("{\"message\": \"Error adding user\"}");
    }

  }
}