package com.ecommerce.service.impl;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address save(AddressDTO addressDTO) {
        validateData(addressDTO);

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setLocation(addressDTO.getLocation());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry("Argentina");
        address.setActive(true);
        address.setProvince(addressDTO.getProvince());
        address.setFullName(addressDTO.getFullName());
        address.setDepartment(addressDTO.getDepartment());

        User user = new User();
        user.setId(addressDTO.getUser().getId());
        address.setUser(user);

        try {
            addressRepository.changeIsActiveToFalse(user.getId());
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public List<AddressDTO> findAllByUserId(Long userId) {
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    public void update(AddressDTO addressDTO) {
        addressRepository.updateAddress(
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

    public void selectAddress(Long addressId, Long userId) {
        try {
            addressRepository.changeIsActiveToFalse(userId);
            addressRepository.activeSelected(userId, addressId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }
    @Override
    public AddressDTO findById(Long id) {
        return addressRepository.findAddressById(id);
    }

    @Override
    public void delete(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    private void validateData(AddressDTO addressDTO){
        if (addressDTO.getFullName() == null || addressDTO.getFullName().isEmpty() ||
                addressDTO.getPostalCode() == null || addressDTO.getPostalCode().isEmpty() ||
                addressDTO.getLocation() == null || addressDTO.getLocation().isEmpty() ||
                addressDTO.getProvince() == null || addressDTO.getProvince().isEmpty() ||
                addressDTO.getStreet() == null || addressDTO.getStreet().isEmpty() ||
                addressDTO.getStreetNumber() == null || addressDTO.getStreetNumber().isEmpty())
        {
            throw new ApiRequestException("El unico campo vacio puede ser piso/departamento!", HttpStatus.BAD_REQUEST);
        }
    }
}
