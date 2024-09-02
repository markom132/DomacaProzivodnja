package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.UserDTO;
import com.domaciproizvodi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAddress("123 Test Str");
        user.setCity("Test City");
        user.setZipCode("15355");
        user.setPhone("123-456-7890");

        UserDTO userDTO = userMapper.toDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getCity(), userDTO.getCity());
        assertEquals(user.getZipCode(), userDTO.getZipCode());
        assertEquals(user.getPhone(), userDTO.getPhone());
    }

    @Test
    void testToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("username");
        userDTO.setEmail("email@email.com");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");
        userDTO.setAddress("123 Test Str");
        userDTO.setCity("Test City");
        userDTO.setZipCode("15355");
        userDTO.setPhone("123-456-7890");
        userDTO.setPassword("password");

        User user = userMapper.toEntity(userDTO);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getCity(), user.getCity());
        assertEquals(userDTO.getZipCode(), user.getZipCode());
        assertEquals(userDTO.getPhone(), user.getPhone());
        assertEquals(userDTO.getPassword(), user.getPassword());
    }

}
