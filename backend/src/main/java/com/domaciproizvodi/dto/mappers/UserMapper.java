package com.domaciproizvodi.dto.mappers;

import com.domaciproizvodi.dto.UserDTO;
import com.domaciproizvodi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDTO toDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setUsername(user.getUsername());
    userDTO.setEmail(user.getEmail());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setAddress(user.getAddress());
    userDTO.setCity(user.getCity());
    userDTO.setZipCode(user.getZipCode());
    userDTO.setPhone(user.getPhone());
    return userDTO;
  }

  public User toEntity(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setAddress(userDTO.getAddress());
    user.setCity(userDTO.getCity());
    user.setZipCode(userDTO.getZipCode());
    user.setPhone(userDTO.getPhone());
    user.setPassword(userDTO.getPassword());
    return user;
  }
}
