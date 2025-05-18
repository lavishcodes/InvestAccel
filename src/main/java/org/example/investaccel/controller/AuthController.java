package org.example.investaccel.controller;

import org.example.investaccel.dto.LoginRequest;
import org.example.investaccel.dto.RegisterRequest;
import org.example.investaccel.entity.User;
import org.example.investaccel.repository.UserRepository;
import org.example.investaccel.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(
            AuthenticationManager authManager,
            JwtUtil jwtUtil,
            UserRepository userRepo,
            PasswordEncoder encoder
    ) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error","Username already taken"));
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(encoder.encode(req.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message","Registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(
                req.getUsername(), req.getPassword()
        );
        authManager.authenticate(authToken);
        String token = jwtUtil.generateToken(req.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
