package com.example.practice.controller;

import com.example.practice.dto.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.practice.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/public/register")
    public ResponseEntity<String> register(@RequestBody UserCreateRequest request){
        userService.createUser(request);
        return ResponseEntity.ok("User register Successfully!");
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminSecret(){
        return ResponseEntity.ok("Welcome, Admin. This is the dashboard");
    }
}
