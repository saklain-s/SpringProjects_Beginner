package com.example.practice.service;


import com.example.practice.dto.UserCreateRequest;
import com.example.practice.entity.Role;
import com.example.practice.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.practice.repository.RoleRepository;
import com.example.practice.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo,PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
        this.roleRepo=roleRepo;
        this.userRepo=userRepo;
    }

    public void createUser(UserCreateRequest request){
        User user = new User();
        user.setUsername(request.getUsername());

        // 3. ENCODE THE PASSWORD HERE 👈
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = request.getRoles()
                .stream()
                .map(roleName ->
                        roleRepo.findByName(roleName)
                                .orElseThrow(() ->
                                        new RuntimeException("Role Not Found")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepo.save(user);
    }

}
