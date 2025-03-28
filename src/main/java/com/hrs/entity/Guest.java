package com.hrs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Guest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  boolean checkedOut;
  boolean checkedIn;
  String firstName;
  String lastName;
  String phoneNumber;
  String roomNumber;
  boolean parcelAvailable;
}
