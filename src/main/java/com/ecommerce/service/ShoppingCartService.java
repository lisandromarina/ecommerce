package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.model.ShoppingCart;

public interface ShoppingCartService {

    void create(Long userId);

    void update(ShoppingCart shoppingCart);

    ShoppingCartDTO findById(Long shoppingCartId);

    void delete(Long shoppingCartId);
}
