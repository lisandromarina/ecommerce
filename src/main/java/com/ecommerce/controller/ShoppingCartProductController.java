package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.service.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCartProduct")
public class ShoppingCartProductController {

    @Autowired
    ShoppingCartProductService shoppingCartProductService;

    @PutMapping("/update")
    public void updateShoppingCartProduct(@RequestBody ShoppingCartProductDTO shoppingCartProductDTO) {
        shoppingCartProductService.updateShoppingCartProducts(shoppingCartProductDTO);
    }

    @DeleteMapping("/delete/{shoppingCartId}/{productId}")
    public void deleteShoppingCartProduct(@PathVariable("shoppingCartId") Long shoppingCartId,
                                          @PathVariable("productId") Long productId) {
        shoppingCartProductService.deleteShoppingCartProduct(shoppingCartId, productId);
    }
}
