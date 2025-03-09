package com.monthlybudget.monthlybudget.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monthlybudget.monthlybudget.models.*;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}