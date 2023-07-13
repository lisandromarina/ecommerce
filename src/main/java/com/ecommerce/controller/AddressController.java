package com.ecommerce.controller;

import com.ecommerce.DTO.AddressDTO;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/findAllByUserId/{userId}")
    public List<AddressDTO> findAllByUserId(@PathVariable("userId")Long userId) {
        return addressService.findAllByUserId(userId);
    }

    @GetMapping("/findById/{id}")
    public AddressDTO findAddressById(@PathVariable("id") Long id) {
        return addressService.findById(id);
    }

    @PostMapping("/save")
    public void saveAddress(@RequestBody AddressDTO addressDTO) {
        addressService.save(addressDTO);
    }

    @PutMapping("/update")
    public void updateAddress(@RequestBody AddressDTO addressDTO) {
        addressService.update(addressDTO);
    }

    @PutMapping("/selectAddress/{addressId}/{userId}")
    public void selectAddress(@PathVariable("addressId") Long addressId, @PathVariable("userId") Long userId) {
        addressService.selectAddress(addressId,userId);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAddressById(@PathVariable("id") Long id) {
        addressService.delete(id);
    }

}
