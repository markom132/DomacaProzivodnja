package com.domaciproizvodi.controller;

import com.domaciproizvodi.config.JwtUtil;
import com.domaciproizvodi.dto.UserDTO;
import com.domaciproizvodi.dto.mappers.UserMapper;
import com.domaciproizvodi.exceptions.UserNotFoundException;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPhone("060 123 4567");
        user.setAddress("TestAddress");
        user.setCity("TestCity");
        user.setZipCode(String.valueOf(15355));

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setPassword("testpassword");
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("Test");
        userDTO.setPhone("060 123 4567");
        userDTO.setAddress("TestAddress");
        userDTO.setCity("TestCity");
        userDTO.setZipCode(String.valueOf(15355));
    }

    @Test
    void testRegisterUser() throws Exception {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(User.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("jwt-token"));

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userService, times(1)).findUserByUsername(user.getUsername());
        verify(jwtUtil, times(1)).generateToken(any());
    }

    @Test
    void testLoginUserFailure() throws Exception {
        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager).authenticate(any());

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetUSerByIdSuccess() throws Exception {
        when(userService.findUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetUserByIdNotFound() throws Exception {
        when(userService.findUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    @WithMockUser(username = "testuser")
    void testVerifyUserSuccess() throws Exception {
        doNothing().when(userService).verifyUser(anyString(), anyString());

        mockMvc.perform(post("/api/users/verify")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"code\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User verified successfully"));

        verify(userService, times(1)).verifyUser("test@example.com", "123456");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testVerifyUserFailure() throws Exception {
        doThrow(new RuntimeException("Invalid verification code")).when(userService).verifyUser(anyString(), anyString());

        mockMvc.perform(post("/api/users/verify")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"code\": \"wrongCode\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Invalid verification code"));

        verify(userService, times(1)).verifyUser(anyString(), anyString());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user);
        when(userService.findAllUsers()).thenReturn(users);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdateUserSuccess() throws Exception {
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).updateUser(anyLong(), any(User.class));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testRequestPasswordResetSuccess() throws Exception {
        doNothing().when(userService).sendPasswordResetCode(anyString());

        mockMvc.perform(post("/api/users/reset-password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"code\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset code sent to test@example.com"));

        verify(userService, times(1)).sendPasswordResetCode(anyString());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testConfirmPasswordResetSuccess() throws Exception {
        doNothing().when(userService).resetPassword(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/users/reset-password/confirm")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"code\": \"123456\", \"newPassword\": \"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset successful"));

        verify(userService, times(1)).resetPassword(anyString(), anyString(), anyString());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/users/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    @WithMockUser(username = "testuser")
    void testHandleUserNotFoundException() throws Exception {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testHandleBadCredentialsException() throws Exception {
        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager).authenticate(any());

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wronguser\", \"password\": \"wrongpass\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

}
