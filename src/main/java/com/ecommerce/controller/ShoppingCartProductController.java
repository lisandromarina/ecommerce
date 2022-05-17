package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.service.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCartProduct")
public class ShoppingCartProductController {

    @Autowired
    ShoppingCartProductService shoppingCartProductService;

    @PutMapping("/update")
    public void updateShoppingCartProduct(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartProductService.updateShoppingCartProducts(shoppingCartDTO);
    }

    @DeleteMapping("/delete/{orderId}/{orderProductId}")
    public void deleteShoppingCartProduct(@PathVariable("orderId") Long orderId,
                                          @PathVariable("orderProductId") Long orderProductId) {
        shoppingCartProductService.deleteShoppingCartProduct(orderId, orderProductId);
    }
}
