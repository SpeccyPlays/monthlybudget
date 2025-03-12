package com.monthlybudget.monthlybudget.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.repos.BudgetEntryRepo;
import com.monthlybudget.monthlybudget.repos.UserRepo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class BudgetEntryController {
    private final BudgetEntryRepo budgetEntryRepo;

    public BudgetEntryController(BudgetEntryRepo budgetEntryRepo) {
        this.budgetEntryRepo = budgetEntryRepo;
    }
}
