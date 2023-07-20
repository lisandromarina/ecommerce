package com.ecommerce.services;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void saveAddressSuccesfully(){
        User user = new User();
        user.setId(1L);

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
        addressDTO.setUser(user);

        doNothing().when(addressRepository).changeIsActiveToFalse(addressDTO.getUser().getId());

        // Mock the addressRepository.save() method to return the saved Address entity
        Address savedAddress = new Address();
        savedAddress.setStreet(addressDTO.getStreet());
        savedAddress.setStreetNumber(addressDTO.getStreetNumber());
        savedAddress.setLocation(addressDTO.getLocation());
        savedAddress.setPostalCode(addressDTO.getPostalCode());
        savedAddress.setCountry("Argentina");
        savedAddress.setActive(true);
        savedAddress.setProvince(addressDTO.getProvince());
        savedAddress.setFullName(addressDTO.getFullName());
        savedAddress.setDepartment(addressDTO.getDepartment());
        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        // Call the save method
        Address resultAddress = addressService.save(addressDTO);

        verify(addressRepository, times(1)).changeIsActiveToFalse(addressDTO.getUser().getId());
        verify(addressRepository, times(1)).save(any(Address.class));

        assertEquals(savedAddress.getFullName(), addressDTO.getFullName());
        assertEquals(savedAddress.getStreet(), addressDTO.getStreet());
        assertEquals(savedAddress.getStreetNumber(), addressDTO.getStreetNumber());
        assertEquals(savedAddress.getLocation(), addressDTO.getLocation());
        assertEquals(savedAddress.getPostalCode(), addressDTO.getPostalCode());
        assertEquals(savedAddress.getProvince(), addressDTO.getProvince());
        assertEquals(savedAddress.getDepartment(), addressDTO.getDepartment());
    }

    @Test
    void testSave_Exception() {
        User user = new User();
        user.setId(1L);

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
        addressDTO.setUser(user);


        // Mock the addressRepository.changeIsActiveToFalse() method to throw an exception
        doThrow(new RuntimeException("Some error")).when(addressRepository).changeIsActiveToFalse(addressDTO.getUser().getId());

        // Call the save method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> addressService.save(addressDTO));

        // Verify that addressRepository.changeIsActiveToFalse() was called
        verify(addressRepository, times(1)).changeIsActiveToFalse(addressDTO.getUser().getId());

        // Verify that addressRepository.save() was not called (since an exception was thrown before saving)
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testUpdate_Successful() {
        // Create a sample AddressDTO
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

        // Call the update method
        addressService.update(addressDTO);

        // Verify that addressRepository.updateAddress() was called with the correct parameters
        verify(addressRepository, times(1)).updateAddress(
                addressDTO.getFullName(),
                addressDTO.getPostalCode(),
                addressDTO.getProvince(),
                addressDTO.getLocation(),
                addressDTO.getStreet(),
                addressDTO.getStreetNumber(),
                addressDTO.getDepartment(),
                addressDTO.getId()
        );
    }

    @Test
    void testSelectAddress_Successful() {
        // Define test data
        Long addressId = 1L;
        Long userId = 2L;

        // Mock the addressRepository.changeIsActiveToFalse() method to not throw any exception
        doNothing().when(addressRepository).changeIsActiveToFalse(userId);

        // Mock the addressRepository.activeSelected() method to not throw any exception
        doNothing().when(addressRepository).activeSelected(userId, addressId);

        // Call the selectAddress method
        addressService.selectAddress(addressId, userId);

        // Verify that addressRepository.changeIsActiveToFalse() and addressRepository.activeSelected() were called
        verify(addressRepository, times(1)).changeIsActiveToFalse(userId);
        verify(addressRepository, times(1)).activeSelected(userId, addressId);
    }

    @Test
    void testSelectAddress_Exception() {
        // Define test data
        Long addressId = 1L;
        Long userId = 2L;

        // Mock the addressRepository.changeIsActiveToFalse() method to throw an exception
        doThrow(new RuntimeException("Some error")).when(addressRepository).changeIsActiveToFalse(userId);

        // Call the selectAddress method and expect an ApiRequestException to be thrown
        assertThrows(ApiRequestException.class, () -> addressService.selectAddress(addressId, userId));

        // Verify that addressRepository.changeIsActiveToFalse() was called
        verify(addressRepository, times(1)).changeIsActiveToFalse(userId);

        // Verify that addressRepository.activeSelected() was not called (since an exception was thrown before)
        verify(addressRepository, never()).activeSelected(userId, addressId);
    }

    @Test
    void testFindAllByUserId_Successful() {
        // Define test data
        Long userId = 1L;
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
        // Mock the addressRepository.findAllByUserId() method to return a list of AddressDTO
        List<AddressDTO> expectedAddressList = Arrays.asList(
                addressDTO, // Replace with actual AddressDTO objects for testing
                addressDTO
        );
        when(addressRepository.findAllByUserId(userId)).thenReturn(expectedAddressList);

        // Call the findAllByUserId method
        List<AddressDTO> resultAddressList = addressService.findAllByUserId(userId);

        // Verify that addressRepository.findAllByUserId() was called with the correct parameter
        verify(addressRepository, times(1)).findAllByUserId(userId);

        // Assert that the returned address list matches the expected list
        assertEquals(expectedAddressList, resultAddressList);
        assertEquals(2, resultAddressList.size());
    }

    @Test
    void testFindById_Successful() {
        // Define test data
        Long addressId = 1L;
        AddressDTO expectedAddressDTO = new AddressDTO(
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
        ); // Replace with the expected AddressDTO for testing

        // Mock the addressRepository.findAddressById() method to return the expected AddressDTO
        when(addressRepository.findAddressById(addressId)).thenReturn(expectedAddressDTO);

        // Call the findById method
        AddressDTO resultAddressDTO = addressService.findById(addressId);

        // Verify that addressRepository.findAddressById() was called with the correct parameter
        verify(addressRepository, times(1)).findAddressById(addressId);

        // Assert that the returned AddressDTO matches the expected AddressDTO
        assertEquals(expectedAddressDTO, resultAddressDTO);
    }

    @Test
    void testDelete_Successful() {
        // Define test data
        Long addressId = 1L;

        // Call the delete method
        addressService.delete(addressId);

        // Verify that addressRepository.deleteById() was called with the correct parameter
        verify(addressRepository, times(1)).deleteById(addressId);
    }
}
