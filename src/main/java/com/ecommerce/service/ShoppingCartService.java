package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.model.ShoppingCart;

public interface ShoppingCartService {

    void update(ShoppingCart shoppingCart);

    ShoppingCartDTO findById(Long shoppingCartId);

    ShoppingCartDTO findByUserId(Long userId);
    void delete(Long shoppingCartId);

}
