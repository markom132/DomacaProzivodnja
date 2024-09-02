package com.domaciproizvodi.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationResponseTest {

    @Test
    void testGetJwt() {
        String jwtToken = "test-jwt-token";
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken);
        assertEquals(jwtToken, authenticationResponse.getJwt());
    }

}
