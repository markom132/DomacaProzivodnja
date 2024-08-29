package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.UserNotFoundException;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.repository.UserRepository;
import jakarta.mail.MessagingException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  private final Map<String, String> resetCodes = new HashMap<>();

  private final Map<String, String> verificationCodes = new HashMap<>();

  @Autowired private EmailService emailService;

  public void sendPasswordResetCode(String email) {
    logger.info("Sending password reset email to user: {}", email);
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isPresent()) {
      String code = generateResetCode();
      resetCodes.put(email, code);
      try {
        emailService.sendPasswordResetEmail(email, code);
        logger.info("Password reset email sent to user: {}", email);
      } catch (MessagingException e) {
        logger.error("Failed to send password reset email to user: {}", email);
        throw new RuntimeException("Failed to send password reset email to user", e);
      }
    } else {
      logger.error("User not found: {}", email);
      throw new RuntimeException("User not found with email: " + email);
    }
  }

  public void resetPassword(String email, String code, String newPassword) {
    logger.info("Trying to reset password for user: {}", email);
    if (resetCodes.containsKey(email) && resetCodes.get(email).equals(code)) {
      Optional<User> userOpt = userRepository.findByEmail(email);
      if (userOpt.isPresent()) {
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        resetCodes.remove(email);
      } else {
        logger.error("User not found: {}", email);
        throw new RuntimeException("User not found with email: " + email);
      }
    } else {
      logger.error("Invalid reset code for user: {}", email);
      throw new RuntimeException("Invalid reset code for user: " + email);
    }
  }

  private String generateResetCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000);
    return String.valueOf(code);
  }

  public User createUser(User user) {
    logger.info("Creating new user with username: {}", user.getUsername());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User createdUser = userRepository.save(user);
    logger.info("User created successfully with id: {}", createdUser.getId());
    String code = generateVerificationCode();
    verificationCodes.put(createdUser.getEmail(), code);

    try {
      emailService.sendVerificationCodeEmail(createdUser.getEmail(), code);
    } catch (MessagingException e) {
      logger.error("Failed to send verification code email to user: {}", createdUser.getEmail());
      throw new RuntimeException("Failed to send verification code email to user", e);
    }
    return createdUser;
  }

  private String generateVerificationCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000);
    return String.valueOf(code);
  }

  public void verifyUser(String email, String code) {
    logger.info("Verifying user with email: {}", email);
    String storedCode = verificationCodes.get(email);
    if (storedCode != null && storedCode.equals(code)) {
      Optional<User> userOpt = userRepository.findByEmail(email);
      if (userOpt.isPresent()) {
        User user = userOpt.get();
        user.setVerified(true);
        userRepository.save(user);
        verificationCodes.remove(email);
      } else {
        logger.error("User not found with email: " + email);
        throw new RuntimeException("User not found with email: " + email);
      }
    } else {
      logger.error("Invalid verification code for user: {}", email);
      throw new RuntimeException("Invalid verification code for user: " + email);
    }
  }

  public Optional<User> findUserById(Long id) {
    logger.info("Fetching user with id: {}", id);
    return userRepository.findById(id);
  }

  public User updateUser(Long id, User updatedUser) {
    logger.info("Updating user with id: {}", id);
    return userRepository
        .findById(id)
        .map(
            user -> {
              user.setUsername(updatedUser.getUsername());
              user.setPassword(updatedUser.getPassword());
              user.setEmail(updatedUser.getEmail());
              user.setFirstName(updatedUser.getFirstName());
              user.setLastName(updatedUser.getLastName());
              user.setPhone(updatedUser.getPhone());
              user.setAddress(updatedUser.getAddress());
              user.setCity(updatedUser.getCity());
              user.setZipCode(updatedUser.getZipCode());

              if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                logger.info("Encoding new password for user with id: {}", id);
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
              }

              User savedUser = userRepository.save(user);
              logger.info("User with id: {} updated successfully", id);
              return savedUser;
            })
        .orElseThrow(
            () -> {
              logger.error("User not found with id: {}", id);
              return new UserNotFoundException("User not found with id: " + id);
            });
  }

  public void deleteUser(Long id) {
    logger.info("Deleting user with id: {}", id);
    if (!userRepository.existsById(id)) {
      logger.error("User not found with id: {}", id);
      throw new UserNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
    logger.info("User with id: {} deleted successfully", id);
  }

  public Optional<User> findUserByUsername(String username) {
    logger.info("Fetching user with username: {}", username);
    return userRepository.findByUsername(username);
  }

  public List<User> findAllUsers() {
    logger.info("Fetching all users");
    return userRepository.findAll();
  }
}
