package com.qa.SpringJWTSecurity.controllers;

import com.qa.SpringJWTSecurity.dtos.user.LoginDTO;
import com.qa.SpringJWTSecurity.dtos.user.ProfileDTO;
import com.qa.SpringJWTSecurity.entities.user.User;
import com.qa.SpringJWTSecurity.security.JwtUtil;
import com.qa.SpringJWTSecurity.services.AuthService;
import com.qa.SpringJWTSecurity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://13.41.210.250:80"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody @Valid User user) {
        System.out.println("Registering user");
        ProfileDTO newRegisteredUser = this.authService.register(user);
        if (newRegisteredUser.getId() > 0) {
            return ResponseEntity.ok(newRegisteredUser);
        }
        else
        {
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
    }

    @GetMapping("/authhealth")
    public String health() {
        System.out.println("Health");
        return "AuthController is running";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO credentials){
        LoginDTO existingUser = this.authService.getProfileByEmail(credentials.getEmail());
        System.out.println("user: " + existingUser.getEmail());
        if (existingUser != null && this.authService.getPasswordEncoder().matches(credentials.getUserPassword(), existingUser.getUserPassword())){
            String token = JwtUtil.generateToken(existingUser.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid Credentials");
    }


}
