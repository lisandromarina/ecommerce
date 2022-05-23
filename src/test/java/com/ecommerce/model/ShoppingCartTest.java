package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ShoppingCartTest {

    @Test
    void gettersAndSettersTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Long oneId = 1L;
        LocalDate oneDataCreate = LocalDate.now();
        User oneUser = mock(User.class);

        shoppingCart.setId(oneId);
        shoppingCart.setDateCreated(oneDataCreate);
        shoppingCart.setUser(oneUser);

        assertEquals(oneId, shoppingCart.getId(), "id should be the same");
        assertEquals(oneDataCreate, shoppingCart.getDateCreated(), "DataCreated should be the same");
        assertEquals(oneUser, shoppingCart.getUser(), "User should be the same");
    }

    @Test
    void constructorTest(){
        Long oneId = 1L;
        LocalDate oneDataCreate = LocalDate.now();
        ShoppingCart shoppingCart = new ShoppingCart(oneId, oneDataCreate);

        assertEquals(oneId, shoppingCart.getId(), "id should be the same");
        assertEquals(oneDataCreate, shoppingCart.getDateCreated(), "DataCreated should be the same");
    }
}