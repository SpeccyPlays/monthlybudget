package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.*;
import com.monthlybudget.monthlybudget.repos.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.monthlybudget.monthlybudget.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserRepo userRepo;

  public UserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

@GetMapping("/users")
  public Iterable<User> findAllEmployees() {
    return this.userRepo.findAll();
  }
}