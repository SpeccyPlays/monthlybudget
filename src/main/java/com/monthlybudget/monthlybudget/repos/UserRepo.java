package com.monthlybudget.monthlybudget.repos;

import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.datatransferobjects.UserDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT new com.monthlybudget.monthlybudget.datatransferobjects.UserDto(u.username, u.role) FROM User u")
    List<UserDto> findAllUsersWithoutPassword();
}