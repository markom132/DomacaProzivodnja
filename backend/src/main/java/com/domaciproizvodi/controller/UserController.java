package com.domaciproizvodi.controller;

import com.domaciproizvodi.dto.UserDTO;
import com.domaciproizvodi.dto.mappers.UserMapper;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUSer = userService.createUser(user);
        return userMapper.toDTO(createdUSer);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOpt = userService.findUserByUsername(userDTO.getUsername());
//Error with password checking //todo fix this api
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            boolean matches = passwordEncoder.matches(userDTO.getPassword(), user.getPassword());

            if (matches) {
                return ResponseEntity.ok(userMapper.toDTO(user));
            }
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return users.stream().map(userMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.findUserById(id);
        return userOpt.map(value -> ResponseEntity.ok(userMapper.toDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(userMapper.toDTO(updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
