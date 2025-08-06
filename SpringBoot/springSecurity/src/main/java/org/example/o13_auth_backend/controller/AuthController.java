package org.example.o13_auth_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.o13_auth_backend.dto.ApiResponse;
import org.example.o13_auth_backend.dto.AuthDTO;
import org.example.o13_auth_backend.dto.RegisterDTO;
import org.example.o13_auth_backend.service.AuthService;
import org.example.o13_auth_backend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
            @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "User registered successfully",
                        authService.register(registerDTO)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new ApiResponse(200,
                "OK", authService.authenticate(authDTO)));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ApiResponse> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                return ResponseEntity.ok(new ApiResponse(200, "Token is valid", null));
            }
        }
        return ResponseEntity.status(401).body(new ApiResponse(401, "Invalid or missing token", null));
    }
}