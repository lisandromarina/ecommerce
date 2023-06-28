package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.impl.ShoppingCartProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCartProduct")
@CrossOrigin(origins = "https://ecommerce-pdq0.onrender.com", allowCredentials = "true")
public class ShoppingCartProductController {

    @Autowired
    ShoppingCartProductServiceImpl shoppingCartProductServiceImpl;

    @PostMapping("/create")
    public void createOrUpdatehoppingCartProduct(@RequestBody ShoppingCartProductDTO shoppingCartProductDTO) {
        shoppingCartProductServiceImpl.createOrUpdate(shoppingCartProductDTO);
    }

    @DeleteMapping("/delete/{shoppingCartId}/{productId}")
    public void deleteShoppingCartProduct(@PathVariable("shoppingCartId") Long shoppingCartId,
                                          @PathVariable("productId") Long productId) {
        shoppingCartProductServiceImpl.delete(shoppingCartId, productId);
    }

}
