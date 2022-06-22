package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.ShoppingCartService;
import com.ecommerce.service.impl.ShoppingCartProductServiceImpl;
import com.ecommerce.service.impl.ShoppingCartServiceImpl;
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
       return shoppingCartServiceImpl.findById(id);
    }

    @GetMapping("/findByUserId/{userId}")
    public ShoppingCartDTO findShoppingCartByUserId(@PathVariable("userId") Long userId) {
        return shoppingCartServiceImpl.findByUserId(userId);
    }
}
