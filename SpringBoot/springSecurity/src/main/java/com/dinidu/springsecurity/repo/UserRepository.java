package com.dinidu.springsecurity.repo;

import com.dinidu.springsecurity.model.User;
import com.dinidu.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if username exists
    boolean existsByUsername(String username);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find users by name containing (case insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    // Find user by username and password (for login)
    Optional<User> findByUsernameAndPassword(String username, String password);

    // Custom query to find users created after a certain date
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date ORDER BY u.createdAt DESC")
    List<User> findUsersCreatedAfter(@Param("date") java.time.LocalDateTime date);

    // Find all users by role
    List<User> findByRole(Role role);
}
