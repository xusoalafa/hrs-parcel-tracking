package com.hrs.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hrs.request.UpdateCheckInRequest;
import com.hrs.request.UpdateCheckOutRequest;
import com.hrs.request.UpdateParcelAvailableRequest;
import com.hrs.response.GuestResponse;
import com.hrs.service.GuestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class GuestControllerTest {

  private MockMvc mockMvc;

  @Mock private GuestService guestService;

  @InjectMocks private GuestController guestController;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private GuestResponse guestResponse;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();

    guestResponse = new GuestResponse();
    guestResponse.setId(1L);
    guestResponse.setFirstName("John");
    guestResponse.setLastName("Doe");
    guestResponse.setPhoneNumber("1234567890");
    guestResponse.setRoomNumber("101");
    guestResponse.setCheckedOut(false);
    guestResponse.setCheckedIn(true);
    guestResponse.setParcelAvailable(true);
  }

  @Test
  void testGetAvailableGuestByName() throws Exception {
    when(guestService.getAvailableGuestsByName(anyString()))
        .thenReturn(Collections.singletonList(guestResponse));

    mockMvc
        .perform(get("/guests/available-guests").param("name", "John"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].firstName").value("John"));

    verify(guestService, times(1)).getAvailableGuestsByName(anyString());
  }

  @Test
  void testUpdateParcelAvailable() throws Exception {
    UpdateParcelAvailableRequest request = new UpdateParcelAvailableRequest();
    request.setParcelAvailable(false);

    when(guestService.updateParcelAvailable(eq(1L), any(UpdateParcelAvailableRequest.class)))
        .thenReturn(guestResponse);

    mockMvc
        .perform(
            patch("/guests/1/update-parcel-status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"));

    verify(guestService, times(1))
        .updateParcelAvailable(eq(1L), any(UpdateParcelAvailableRequest.class));
  }

  @Test
  void testUpdateCheckOutStatus() throws Exception {
    UpdateCheckOutRequest request = new UpdateCheckOutRequest();
    request.setCheckedOut(true);

    when(guestService.updateCheckoutStatus(eq(1L), any(UpdateCheckOutRequest.class)))
        .thenReturn(guestResponse);

    mockMvc
        .perform(
            patch("/guests/1/check-out")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.checkedOut").value(false));

    verify(guestService, times(1)).updateCheckoutStatus(eq(1L), any(UpdateCheckOutRequest.class));
  }

  @Test
  void testUpdateCheckInStatus() throws Exception {
    UpdateCheckInRequest request = new UpdateCheckInRequest();
    request.setCheckedIn(true);

    when(guestService.updateCheckInStatus(eq(1L), any(UpdateCheckInRequest.class)))
        .thenReturn(guestResponse);

    mockMvc
        .perform(
            patch("/guests/1/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.checkedIn").value(true));

    verify(guestService, times(1)).updateCheckInStatus(eq(1L), any(UpdateCheckInRequest.class));
  }
}
