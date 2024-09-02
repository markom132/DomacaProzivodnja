package com.domaciproizvodi.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtUtil.extractUsername("valid-token")).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid-token", userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(jwtUtil, times(1)).extractUsername("valid-token");
    }

    @Test
    @WithMockUser
    void testDoFilterInternalExpiredToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer expired-token");
        when(jwtUtil.extractUsername("expired-token")).thenThrow(new ExpiredJwtException(null, null, "Expired JWT token"));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    @WithMockUser
    void testDoFilterInternalNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }
}
