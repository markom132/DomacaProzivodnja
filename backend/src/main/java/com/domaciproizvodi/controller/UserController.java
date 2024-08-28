package com.domaciproizvodi.controller;

import com.domaciproizvodi.config.AuthenticationResponse;
import com.domaciproizvodi.config.JwtUtil;
import com.domaciproizvodi.dto.UserDTO;
import com.domaciproizvodi.dto.mappers.UserMapper;
import com.domaciproizvodi.exceptions.UserNotFoundException;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            logger.info("Attempting to register new user with username: {}", userDTO.getUsername());
            User user = userMapper.toEntity(userDTO);
            User createdUser = userService.createUser(user);
            logger.info("User registered successfully with id: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(createdUser));
        } catch (RuntimeException e) {
            logger.error("Error occurred while registering user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        try {
            userService.verifyUser(email, code);
            return ResponseEntity.status(HttpStatus.OK).body("User verified successfully");
        } catch (RuntimeException e) {
            logger.error("Error occurred while verifying user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            logger.info("Attempting to authenticate user with username: {}", userDTO.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for username: {}", userDTO.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userService.findUserByUsername(userDTO.getUsername())
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", userDTO.getUsername());
                    return new UserNotFoundException("User not found with username: " + userDTO.getUsername());
                });
        String jwt = jwtUtil.generateToken(user);
        logger.info("User authenticated successfully with username: {}", userDTO.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse(jwt));
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users.stream().map(userMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        User user = userService.findUserById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new UserNotFoundException("User not found with id: " + id);
                });
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            logger.info("Attempting to update user with id: {}", id);
            User user = userMapper.toEntity(userDTO);
            User updatedUser = userService.updateUser(id, user);
            logger.info("User updated successfully with id: {}", updatedUser.getId());
            return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(updatedUser));
        } catch (RuntimeException e) {
            logger.error("Error occurred while updating user with id: {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.sendPasswordResetCode(email);
        return ResponseEntity.ok("Password reset code sent to " + email);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String newPassword = request.get("newPassword");
        userService.resetPassword(email, code, newPassword);
        return ResponseEntity.ok("Password reset successful");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Attempting to delete user with id: {}", id);
        userService.deleteUser(id);
        logger.info("User deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        logger.error("UserNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        logger.warn("BadCredentialsException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
