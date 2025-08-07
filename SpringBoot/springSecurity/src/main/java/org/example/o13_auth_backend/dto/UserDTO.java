package org.example.o13_auth_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    // Getters and setters
    private Long id;
    private String username;
    private String role;
    private String email;
    private String joined;
    private String lastUpdated;
    private String status;

}
