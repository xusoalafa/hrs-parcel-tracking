package com.hrs.service;

import com.hrs.entity.Guest;
import com.hrs.repository.GuestRepository;
import com.hrs.request.GuestCreateRequest;
import com.hrs.request.UpdateCheckInRequest;
import com.hrs.request.UpdateCheckOutRequest;
import com.hrs.request.UpdateParcelAvailableRequest;
import com.hrs.response.GuestResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mapper.GuestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GuestService {
  GuestRepository guestRepository;

  @Transactional(readOnly = true)
  public List<GuestResponse> getAllGuests() {
    List<Guest> guests = guestRepository.findAll();
    // Map entity to DTO and return
    return guests.stream().map(GuestMapper.INSTANCE::toGuestResponse).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<GuestResponse> getAvailableGuestsByName(String name) {
    // if name is null then allowing to search name value is "" to get all available guest who
    // checked in
    List<Guest> guests = guestRepository.searchAvailableGuestsByName(name == null ? "" : name);

    // If null, return empty list
    if (guests == null) {
      return Collections.emptyList();
    }

    // Map entity to DTO and return
    return guests.stream().map(GuestMapper.INSTANCE::toGuestResponse).collect(Collectors.toList());
  }

  @Transactional
  public GuestResponse insertGuest(final GuestCreateRequest request) {
    // Map DTO to Entity
    Guest guest = GuestMapper.INSTANCE.toGuest(request);

    // Save entity to DB
    Guest savedGuest = guestRepository.save(guest);

    // Convert entity to response DTO
    return GuestMapper.INSTANCE.toGuestResponse(savedGuest);
  }

  @Transactional
  public GuestResponse update(final GuestCreateRequest request) {
    // Map DTO to Entity
    Guest guest = GuestMapper.INSTANCE.toGuest(request);

    // Save entity to DB
    Guest savedGuest = guestRepository.save(guest);

    // Convert entity to response DTO
    return GuestMapper.INSTANCE.toGuestResponse(savedGuest);
  }

  @Transactional
  public GuestResponse updateParcelAvailable(Long id, UpdateParcelAvailableRequest request) {
    Guest guest =
        guestRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

    // update only ParcelAvailable field
    guest.setParcelAvailable(request.isParcelAvailable());
    Guest updatedGuest = guestRepository.save(guest);

    // Convert entity to response DTO
    return GuestMapper.INSTANCE.toGuestResponse(updatedGuest);
  }

  @Transactional
  public GuestResponse updateCheckoutStatus(Long id, UpdateCheckOutRequest request) {
    Guest guest =
        guestRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

    if (guest.isParcelAvailable() && request.isCheckedOut()) {
      throw new RuntimeException(
          "The guest has an available parcel. Return the parcel to the guest and update its status before checkout");
    }

    // update only CheckedOut field
    guest.setCheckedOut(request.isCheckedOut());
    Guest updatedGuest = guestRepository.save(guest);

    // Convert entity to response DTO
    return GuestMapper.INSTANCE.toGuestResponse(updatedGuest);
  }

  @Transactional
  public GuestResponse updateCheckInStatus(Long id, UpdateCheckInRequest request) {
    Guest guest =
        guestRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

    // update only CheckedIn field
    guest.setCheckedIn(request.isCheckedIn());
    Guest updatedGuest = guestRepository.save(guest);

    // Convert entity to response DTO
    return GuestMapper.INSTANCE.toGuestResponse(updatedGuest);
  }
}
