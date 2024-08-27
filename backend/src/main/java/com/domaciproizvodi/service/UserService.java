package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.UserNotFoundException;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        logger.info("User created successfully with id: {}", createdUser.getId());
        return createdUser;
    }

    public Optional<User> findUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        logger.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> {
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
                .orElseThrow(() -> {
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
