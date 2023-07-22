package com.ecommerce.controller;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void testFindAllByUserId() throws Exception {
        // Define test data
        Long userId = 1L;
        List<AddressDTO> addresses = new ArrayList<>(); // Replace with your test data

        // Mock the addressService.findAllByUserId() method
        when(addressService.findAllByUserId(userId)).thenReturn(addresses);

        // Perform the GET request to /address/findAllByUserId/{userId}
        mockMvc.perform(get("/address/findAllByUserId/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(addresses.size())));

        // Verify that the addressService.findAllByUserId() method was called with the correct userId
        verify(addressService, times(1)).findAllByUserId(userId);
    }

    @Test
    void testFindAddressById() throws Exception {
        // Define test data
        Long addressId = 1L;
        AddressDTO address = new AddressDTO(
                1L,
                "oneFullName",
                "oneStreet",
                "oneStreetNumber",
                "oneDepartment",
                "onePostalCode",
                "oneLocation",
                "oneProvince",
                "oneCountry",
                true,
                false
        );

        // Mock the addressService.findById() method
        when(addressService.findById(addressId)).thenReturn(address);

        // Perform the GET request to /address/findById/{id}
        mockMvc.perform(get("/address/findById/{id}", addressId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify that the addressService.findById() method was called with the correct addressId
        verify(addressService, times(1)).findById(addressId);
    }

    @Test
    void testSaveAddress() throws Exception {
        // Define test data
        AddressDTO addressDTO = new AddressDTO(
                1L,
                "oneFullName",
                "oneStreet",
                "oneStreetNumber",
                "oneDepartment",
                "onePostalCode",
                "oneLocation",
                "oneProvince",
                "oneCountry",
                true,
                false
        );

        // Perform the POST request to /address/save
        mockMvc.perform(post("/address/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressDTO))) // Convert the AddressDTO to JSON string
                .andExpect(status().isOk());

        // Use ArgumentCaptor to capture the argument passed to addressService.save()
        ArgumentCaptor<AddressDTO> argumentCaptor = ArgumentCaptor.forClass(AddressDTO.class);
        verify(addressService, times(1)).save(argumentCaptor.capture());

        // Verify that the captured argument is the same as the expected AddressDTO
        AddressDTO capturedAddressDTO = argumentCaptor.getValue();
        assertEquals(addressDTO.getId(), capturedAddressDTO.getId());
        assertEquals(addressDTO.getFullName(), capturedAddressDTO.getFullName());
    }

    @Test
    void testUpdateAddress() throws Exception {
        // Define test data
        AddressDTO addressDTO = new AddressDTO(
                1L,
                "oneFullName",
                "oneStreet",
                "oneStreetNumber",
                "oneDepartment",
                "onePostalCode",
                "oneLocation",
                "oneProvince",
                "oneCountry",
                true,
                false
        );

        // Create a captor for the AddressDTO argument
        ArgumentCaptor<AddressDTO> argumentCaptor = ArgumentCaptor.forClass(AddressDTO.class);

        // Perform the PUT request to /address/update
        mockMvc.perform(put("/address/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressDTO))) // Convert the AddressDTO to JSON string
                .andExpect(status().isOk());

        // Verify that the addressService.update() method was called with the correct AddressDTO
        verify(addressService, times(1)).update(argumentCaptor.capture());

        // Get the captured AddressDTO from the captor
        AddressDTO capturedAddressDTO = argumentCaptor.getValue();

        // Assert that the captured AddressDTO matches the expected data
        assertEquals(addressDTO.getId(), capturedAddressDTO.getId());
        assertEquals(addressDTO.getFullName(), capturedAddressDTO.getFullName());
    }
    @Test
    void testSelectAddress() throws Exception {
        // Define test data
        Long addressId = 1L;
        Long userId = 1L;

        // Perform the PUT request to /address/selectAddress/{addressId}/{userId}
        mockMvc.perform(put("/address/selectAddress/{addressId}/{userId}", addressId, userId))
                .andExpect(status().isOk());

        // Verify that the addressService.selectAddress() method was called with the correct addressId and userId
        verify(addressService, times(1)).selectAddress(addressId, userId);
    }

    @Test
    void testDeleteAddressById() throws Exception {
        // Define test data
        Long addressId = 1L;

        // Perform the DELETE request to /address/delete/{id}
        mockMvc.perform(delete("/address/delete/{id}", addressId))
                .andExpect(status().isOk());

        // Verify that the addressService.delete() method was called with the correct addressId
        verify(addressService, times(1)).delete(addressId);
    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}