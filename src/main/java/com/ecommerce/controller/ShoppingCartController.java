package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/create")
    public void createShoppingCart() {
        shoppingCartService.createShoppingCart();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShoppingCart(@PathVariable("id") Long id) {
        shoppingCartService.deleteShoppingCartById(id);
    }

    @GetMapping("/findById/{id}")
    public ShoppingCartDTO findShoppingCartById(@PathVariable("id") Long id) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.findShoppingCartDTOById(id);
        Set<ShoppingCartProductDTO> shoppingCartProductDTO = shoppingCartService.findShoppingCartProductsByShoppingCartId(id);
        shoppingCartDTO.setShoppingCartProductsDTO(shoppingCartProductDTO);
        return shoppingCartDTO;
    }
}
