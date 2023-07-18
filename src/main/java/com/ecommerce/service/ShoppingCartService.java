package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.model.ShoppingCart;

import java.security.Principal;

public interface ShoppingCartService {

    void update(ShoppingCart shoppingCart);

    ShoppingCartDTO findById(Long shoppingCartId);

    ShoppingCartDTO findByUserId(Long userId);
    void delete(Long shoppingCartId);

    ShoppingCartDTO checkoutShoppingCart(Principal principal);

}
