package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.ShoppingCartProductServiceImpl;
import com.ecommerce.service.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Autowired
    ShoppingCartProductServiceImpl shoppingCartProductServiceImpl;

    @PostMapping("/create/{userId}")
    public void createShoppingCart(@PathVariable("userId") Long userId) {
        shoppingCartServiceImpl.create(userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShoppingCart(@PathVariable("id") Long id) {
        shoppingCartServiceImpl.delete(id);
    }

    @GetMapping("/findById/{id}")
    public ShoppingCartDTO findShoppingCartById(@PathVariable("id") Long id) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartServiceImpl.findById(id);

        Set<ShoppingCartProductDTO> shoppingCartProductDTO = shoppingCartProductServiceImpl
                .findByShoppingCartId(id);
        shoppingCartDTO.setShoppingCartProductsDTO(shoppingCartProductDTO);

        return shoppingCartDTO;
    }
}
