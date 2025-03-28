package com.hrs.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestResponse {
  Long id;
  boolean checkedOut;
  boolean checkedIn;
  String firstName;
  String lastName;
  String phoneNumber;
  String roomNumber;
  boolean parcelAvailable;
}
