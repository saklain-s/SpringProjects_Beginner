package com.example.practice.repository;

import com.example.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // findBy + Username (U must be uppercase for the method name)
    Optional<User> findByUsername(String username);
}
