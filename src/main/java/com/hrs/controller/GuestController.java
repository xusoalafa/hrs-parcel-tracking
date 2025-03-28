package com.hrs.controller;

import com.hrs.request.GuestCreateRequest;
import com.hrs.request.UpdateCheckInRequest;
import com.hrs.request.UpdateCheckOutRequest;
import com.hrs.request.UpdateParcelAvailableRequest;
import com.hrs.response.GuestResponse;
import com.hrs.service.GuestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GuestController {
  GuestService guestService;

  @PostMapping
  public ResponseEntity<GuestResponse> createPreBookGuest(
      @RequestBody @Valid GuestCreateRequest request) {
    GuestResponse response = guestService.insertGuest(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<GuestResponse>> getAllGuest() {
    List<GuestResponse> guests = guestService.getAllGuests();
    return ResponseEntity.ok(guests);
  }

  @GetMapping("/available-guests")
  public ResponseEntity<List<GuestResponse>> getAvailableGuestsByName(
      @RequestParam(required = false) String name) {
    List<GuestResponse> guests = guestService.getAvailableGuestsByName(name);
    return ResponseEntity.ok(guests);
  }

  @PatchMapping("/{id}/update-parcel-status")
  public ResponseEntity<GuestResponse> updateParcelStatus(
      @PathVariable Long id, @RequestBody UpdateParcelAvailableRequest request) {
    GuestResponse updatedGuest = guestService.updateParcelAvailable(id, request);
    return ResponseEntity.ok(updatedGuest);
  }

  @PatchMapping("/{id}/check-out")
  public ResponseEntity<GuestResponse> updateCheckOutStatus(
      @PathVariable Long id, @RequestBody UpdateCheckOutRequest request) {
    GuestResponse updatedGuest = guestService.updateCheckoutStatus(id, request);
    return ResponseEntity.ok(updatedGuest);
  }

  @PatchMapping("/{id}/check-in")
  public ResponseEntity<GuestResponse> updateCheckInStatus(
      @PathVariable Long id, @RequestBody UpdateCheckInRequest request) {
    GuestResponse updatedGuest = guestService.updateCheckInStatus(id, request);
    return ResponseEntity.ok(updatedGuest);
  }
}
