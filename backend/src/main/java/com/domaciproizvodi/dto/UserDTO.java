package com.domaciproizvodi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

  private Long id;

  @NotBlank(message = "Username is mandatory")
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "First name is mandatory")
  @Size(max = 50, message = "First name must be less than 50 characters")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Size(max = 50, message = "Last name must be less than 50 characters")
  private String lastName;

  @NotBlank(message = "Phone is mandatory")
  @Pattern(
      regexp = "^(\\+\\d{1,3}[- ]?)?\\d{3}[- ]?\\d{3}[- ]?\\d{4}$",
      message = "Phone number is invalid")
  private String phone;

  @NotBlank(message = "Address is mandatory")
  @Size(max = 100, message = "Address must be less than 100 characters")
  private String address;

  @NotBlank(message = "City is mandatory")
  @Size(max = 50, message = "City must be less than 50 characters")
  private String city;

  @NotBlank(message = "Zip code is mandatory")
  @Pattern(regexp = "^[0-9]{5}$", message = "Zip must be a 5 digit number")
  private String zipCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}
