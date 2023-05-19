package com.ecommerce.service.impl;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setLocation(addressDTO.getLocation());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());
        address.setActive(true);

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
}
