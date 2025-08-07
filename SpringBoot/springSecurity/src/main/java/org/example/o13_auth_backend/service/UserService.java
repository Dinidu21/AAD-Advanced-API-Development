package org.example.o13_auth_backend.service;

import org.example.o13_auth_backend.dto.ApiResponse;
import org.example.o13_auth_backend.dto.UserDTO;
import org.example.o13_auth_backend.entity.User;
import org.example.o13_auth_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Fetch all users for the User Management table
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Fetch the logged-in user's profile
    public UserDTO getUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return convertToDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Convert User entity to DTO with additional fields
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().toString());
        // Placeholder values for fields not in User entity
        dto.setEmail(user.getUsername() + "@example.com"); // Inferred email
        dto.setJoined(LocalDateTime.now().minusDays(30).toString()); // Example joined date
        dto.setLastUpdated(LocalDateTime.now().toString()); // Example last updated
        dto.setStatus("Active"); // Default status
        return dto;
    }
}