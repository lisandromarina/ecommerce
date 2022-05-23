package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ShoppingCartProductTest {
    @Test
    void gettersAndSettersTest() {
        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
        ShoppingCartProductPK onePk = mock(ShoppingCartProductPK.class);
        Integer quantity = 5;
        Double sellPrice = 10.1;

        shoppingCartProduct.setPk(onePk);
        shoppingCartProduct.setQuantity(quantity);
        shoppingCartProduct.setSellPrice(sellPrice);

        assertEquals(onePk, shoppingCartProduct.getPk(), "pk should be the same");
        assertEquals(quantity, shoppingCartProduct.getQuantity(), "Quantity should be the same");
        assertEquals(sellPrice, shoppingCartProduct.getSellPrice(), "sellPrice should be the same");
    }
}