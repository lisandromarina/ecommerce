package com.ecommerce.service;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.DTO.CategoryDTO;
import com.ecommerce.model.Address;
import com.ecommerce.model.Category;

import java.util.List;

public interface AddressService {

    Address save(AddressDTO addressDTO);

    List<AddressDTO> findAllByUserId(Long userId);

    void update(AddressDTO addressDTO);

    void selectAddress(Long addressId, Long userId);

    AddressDTO findById(Long id);

    void delete(Long id);

}
