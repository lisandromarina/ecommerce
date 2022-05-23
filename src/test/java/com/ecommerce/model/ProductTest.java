package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void gettersAndSettersTest() {
        Product product = new Product();
        Long oneId = 1L;
        String oneName = "oneName";
        Double onePrice = 10.1;

        product.setId(oneId);
        product.setName(oneName);
        product.setPrice(onePrice);

        assertEquals(oneId, product.getId(), "id should be the same");
        assertEquals(onePrice, product.getPrice(), "Price should be the same");
        assertEquals(oneName, product.getName(), "Name should be the same");
    }
}