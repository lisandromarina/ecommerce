package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.impl.ShoppingCartProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCartProduct")
public class ShoppingCartProductController {

    @Autowired
    ShoppingCartProductServiceImpl shoppingCartProductServiceImpl;

    @PutMapping("/update")
    public void updateShoppingCartProduct(@RequestBody ShoppingCartProductDTO shoppingCartProductDTO) {
        shoppingCartProductServiceImpl.update(shoppingCartProductDTO);
    }

    @DeleteMapping("/delete/{shoppingCartId}/{productId}")
    public void deleteShoppingCartProduct(@PathVariable("shoppingCartId") Long shoppingCartId,
                                          @PathVariable("productId") Long productId) {
        shoppingCartProductServiceImpl.delete(shoppingCartId, productId);
    }
}
