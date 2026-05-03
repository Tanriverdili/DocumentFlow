package com.example.api.controller;
import com.example.api.dto.AuthRequest;
import com.example.api.dto.RegisterRequest;
import com.example.api.entity.UserEntity;
import com.example.api.enums.Role;
import com.example.api.repository.UserRepository;
import com.example.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "User created successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }
}

























//public class AuthController {
//
//    private final JwtService jwtService;
//
//    @PostMapping("/login")
//    public String login(@RequestBody AuthRequest request) {
//
//        if (request.getUsername().equals("admin") && request.getPassword().equals("123")) {
//            return jwtService.generateToken(request.getUsername());
//        }
//
//        if (request.getUsername().equals("user") && request.getPassword().equals("123")) {
//            return jwtService.generateToken("user");
//        }
//
//        if (request.getUsername().equals("approver") && request.getPassword().equals("123")) {
//            return jwtService.generateToken("approver");
//        }
//        throw new RuntimeException("Invalid credentials");
//    }
//
//
//    }

