package org.example.o13_auth_backend.controller;

import org.example.o13_auth_backend.dto.ApiResponse;
import org.example.o13_auth_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse(200, "Users retrieved successfully", userService.getAllUsers()));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile() {
        try {
            return ResponseEntity.ok(new ApiResponse(200, "Profile retrieved successfully", userService.getUserProfile()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(404, "User not found", null));
        }
    }
}