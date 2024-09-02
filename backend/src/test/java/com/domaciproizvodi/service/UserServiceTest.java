package com.domaciproizvodi.service;

import com.domaciproizvodi.exceptions.UserNotFoundException;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("test@example.com");
    }

    @Test
    void testCreateUserSuccess() throws MessagingException {
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createduser = userService.createUser(user);

        assertNotNull(createduser);
        assertEquals(user.getId(), createduser.getId());

        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).sendVerificationCodeEmail(anyString(), anyString());
    }

    @Test
    void testCreateUserEmailSendFailure() throws MessagingException {
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);
        doThrow(new MessagingException("Failed to send email")).when(emailService).sendVerificationCodeEmail(anyString(), anyString());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(user));

        assertEquals("Failed to send verification code email to user", exception.getMessage());

        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).sendVerificationCodeEmail(anyString(), anyString());
    }

    @Test
    void testFindUserByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(1L);

        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setUsername("updatedUsername");
        updatedUser.setPassword("newPassword");

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setUsername("updatedUsername");
        updatedUser.setPassword("newPassword");

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updatedUser));

        assertEquals("User not found with id: 1", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found with id: 1", exception.getMessage());

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void testSendPasswordResetEmailCodeSuccess() throws MessagingException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.sendPasswordResetCode(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(emailService, times(1)).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    void testSendPasswordResetEmailCodeNotFound() throws MessagingException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.sendPasswordResetCode(anyString()));

        assertEquals("User not found with email: ", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(any());
        verify(emailService, never()).sendPasswordResetEmail(anyString(),anyString());
    }

    @Test
    void testResetPasswordSuccess() throws MessagingException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        Map<String, String> resetCodes = new HashMap<>();
        doAnswer(invocation -> {
            String email = invocation.getArgument(0);
            String code = invocation.getArgument(1);
            resetCodes.put(email, code);
            return null;
        }).when(emailService).sendPasswordResetEmail(anyString(), anyString());

        userService.sendPasswordResetCode(user.getEmail());
        userService.resetPassword(user.getEmail(), resetCodes.get("test@example.com"), "newPassword");

        verify(userRepository, times(2)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testResetPasswordInvalidCode() throws MessagingException {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.sendPasswordResetCode(user.getEmail());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.resetPassword(user.getEmail(), "wrongCode", "newPass"));

        assertEquals("Invalid reset code for user: test@example.com", exception.getMessage());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(emailService, times(1)).sendPasswordResetEmail(anyString(), anyString());
        verify(userRepository, never()).save(any(User.class));
    }

}
