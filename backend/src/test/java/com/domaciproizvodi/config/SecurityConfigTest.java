package com.domaciproizvodi.config;

import com.domaciproizvodi.controller.OrderController;
import com.domaciproizvodi.controller.UserController;
import com.domaciproizvodi.dto.mappers.UserMapper;
import com.domaciproizvodi.model.User;
import com.domaciproizvodi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class, SecurityConfig.class})
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoderBean() {
        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder.encode("test")).isNotEqualTo("test");
    }

    @Test
    public void testAuthenticationManagerBean() {
        assertThat(authenticationManager).isNotNull();
    }

    @Test
    public void testSecurityFilterChain() throws Exception {
        // Mock AuthenticationManager to return a successful authentication
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));

        // Mock UserService to return a valid User when searching by username
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("test");
        mockUser.setPassword("test");

        when(userService.findUserByUsername("test")).thenReturn(Optional.of(mockUser));

        // Mock JwtUtil to generate a valid token
        when(jwtUtil.generateToken(mockUser)).thenReturn("mocked-jwt-token");

        // Test login
        mockMvc.perform(post("/api/users/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"username\":\"test\",\"password\":\"test\"}"))
                .andExpect(status().isOk());

        // Test unauthorized access to a protected endpoint
        mockMvc.perform(get("/api/order-items")
                        .header("Authorization", "Bearer mocked-jwt-token"))
                .andExpect(status().isForbidden());
    }

}
