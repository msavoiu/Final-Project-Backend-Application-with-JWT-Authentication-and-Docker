package controllers;

import util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import dto.UserDTO;
import entities.User;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private repositories.UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

        // check if username taken
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username already exists"));
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPasswordHash()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", token));
    }
}