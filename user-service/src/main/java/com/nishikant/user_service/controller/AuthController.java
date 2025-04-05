package com.nishikant.user_service.controller;


import com.nishikant.user_service.dto.User;
import com.nishikant.user_service.repository.UserRepository;
import com.nishikant.user_service.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash Password
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent() && passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername(), optionalUser.get().getRoles());
            return Map.of("token", token);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
