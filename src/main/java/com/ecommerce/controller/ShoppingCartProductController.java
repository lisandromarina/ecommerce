package com.ecommerce.controller;

import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCartProduct;
import com.ecommerce.service.ShoppingCartProductService;
import com.ecommerce.service.impl.ShoppingCartProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCartProduct")
public class ShoppingCartProductController {

    @Autowired
    ShoppingCartProductService shoppingCartProductServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<ShoppingCartProduct> createShoppingCartProduct(@RequestBody ShoppingCartProductDTO shoppingCartProductDTO) {
        return shoppingCartProductServiceImpl.createShoppingCartProduct(shoppingCartProductDTO);
    }

    @PatchMapping("/update")
    public  ResponseEntity<ShoppingCartProduct> updateShoppingCartProduct(@RequestBody ShoppingCartProductDTO shoppingCartProductDTO) {
        return shoppingCartProductServiceImpl.updateShoppingCartProduct(shoppingCartProductDTO);
    }

    @DeleteMapping("/delete/{shoppingCartId}/{productId}")
    public void deleteShoppingCartProduct(@PathVariable("shoppingCartId") Long shoppingCartId,
                                          @PathVariable("productId") Long productId) {
        shoppingCartProductServiceImpl.delete(shoppingCartId, productId);
    }

}
