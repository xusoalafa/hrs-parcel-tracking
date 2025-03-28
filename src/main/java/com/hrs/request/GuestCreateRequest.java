package com.hrs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestCreateRequest {
  @NotBlank(message = "First name cannot be empty")
  @Size(max = 50, message = "First name must be at most 50 characters")
  String firstName;

  @NotBlank(message = "Last name cannot be empty")
  @Size(max = 50, message = "Last name must be at most 50 characters")
  String lastName;

  @NotBlank(message = "Phone number cannot be empty")
  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
  String phoneNumber;

  @NotBlank(message = "Room number cannot be empty")
  @Size(max = 10, message = "Room number must be at most 10 characters")
  String roomNumber;
}
