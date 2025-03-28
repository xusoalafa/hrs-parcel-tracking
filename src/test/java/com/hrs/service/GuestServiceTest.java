package com.hrs.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hrs.entity.Guest;
import com.hrs.repository.GuestRepository;
import com.hrs.request.GuestCreateRequest;
import com.hrs.request.UpdateCheckInRequest;
import com.hrs.request.UpdateCheckOutRequest;
import com.hrs.request.UpdateParcelAvailableRequest;
import com.hrs.response.GuestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

  @Mock private GuestRepository guestRepository;

  @InjectMocks private GuestService guestService;

  private Guest guest;

  @BeforeEach
  void setUp() {
    guest = new Guest();
    guest.setId(1L);
    guest.setFirstName("John");
    guest.setLastName("Doe");
    guest.setPhoneNumber("1234567890");
    guest.setRoomNumber("101");
    guest.setCheckedOut(false);
    guest.setCheckedIn(true);
    guest.setParcelAvailable(true);
  }

  @Test
  void testGetAllGuests() {
    when(guestRepository.findAll()).thenReturn(Arrays.asList(guest));

    List<GuestResponse> guests = guestService.getAllGuests();

    assertFalse(guests.isEmpty());
    assertEquals(1, guests.size());
    assertEquals("John", guests.get(0).getFirstName());
    verify(guestRepository, times(1)).findAll();
  }

  @Test
  void testInsertGuest() {
    GuestCreateRequest request = new GuestCreateRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setPhoneNumber("1234567890");
    request.setRoomNumber("101");

    when(guestRepository.save(any(Guest.class))).thenReturn(guest);

    GuestResponse response = guestService.insertGuest(request);

    assertNotNull(response);
    assertEquals("John", response.getFirstName());
    verify(guestRepository, times(1)).save(any(Guest.class));
  }

  @Test
  void testUpdateParcelAvailable() {
    UpdateParcelAvailableRequest request = new UpdateParcelAvailableRequest();
    request.setParcelAvailable(false);

    when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
    when(guestRepository.save(any(Guest.class))).thenReturn(guest);

    GuestResponse response = guestService.updateParcelAvailable(1L, request);

    assertNotNull(response);
    assertFalse(response.isParcelAvailable());
    verify(guestRepository, times(1)).save(any(Guest.class));
  }

  @Test
  void testUpdateCheckOutStatus() {
    UpdateCheckOutRequest request = new UpdateCheckOutRequest();
    request.setCheckedOut(true);

    when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      guestService.updateCheckoutStatus(1L, request);
    });
    assertEquals(
            "The guest has an available parcel. Return the parcel to the guest and update its status before checkout",
            exception.getMessage()
    );

    verify(guestRepository, never()).save(any(Guest.class));
  }

  @Test
  void testUpdateCheckInStatus() {
    UpdateCheckInRequest request = new UpdateCheckInRequest();
    request.setCheckedIn(true);

    when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
    when(guestRepository.save(any(Guest.class))).thenReturn(guest);

    GuestResponse response = guestService.updateCheckInStatus(1L, request);

    assertNotNull(response);
    assertTrue(response.isCheckedIn());
    verify(guestRepository, times(1)).save(any(Guest.class));
  }
}
