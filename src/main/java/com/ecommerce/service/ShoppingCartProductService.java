package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartProductDTO;

import java.util.Set;

public interface ShoppingCartProductService {

    void create(ShoppingCartProductDTO shoppingCartProductDTO);

    void delete(Long shoppingCartId, Long productId);

    Set<ShoppingCartProductDTO> findByShoppingCartId(Long id);
}
