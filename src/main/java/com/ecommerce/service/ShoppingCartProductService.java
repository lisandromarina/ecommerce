package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCartProduct;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ShoppingCartProductService {

    void delete(Long shoppingCartId, Long productId);

    Set<ShoppingCartProductDTO> findByShoppingCartId(Long id);

    ResponseEntity<ShoppingCartProduct> updateShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);

    ResponseEntity<ShoppingCartProduct> createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);
}
