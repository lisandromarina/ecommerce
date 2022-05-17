package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.ShoppingCartProduct;
import com.ecommerce.model.ShoppingCartProductPK;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartProductService {

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    //Create an ShoppingCart from ShoppingCartDTO
    private ShoppingCart createShoppingCartFromDTO(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDTO.getId());
        return shoppingCart;
    }

    //Create an ShoppingCartProductPk
    private ShoppingCartProductPK createShoppingCartProductPk(ShoppingCart shoppingCart, Product product) {
        ShoppingCartProductPK shoppingCartProductPK = new ShoppingCartProductPK();
        shoppingCartProductPK.setOrder(shoppingCart);
        shoppingCartProductPK.setProduct(product);
        return shoppingCartProductPK;
    }

    //Create ShoppingCartProduct from a ShoppingCartProductDTO
    private ShoppingCartProduct createShoppingCartProduct(ShoppingCartProductPK shoppingCartProductPK, ShoppingCartProductDTO shoppingCartProductDTO) {
        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
        shoppingCartProduct.setPk(shoppingCartProductPK);
        shoppingCartProduct.setQuantity(shoppingCartProductDTO.getQuantity());
        shoppingCartProduct.setSellPrice(shoppingCartProductDTO.getProductDTO().getPrice());
        return shoppingCartProduct;
    }

    //update orderProducts for a specific order
    public void updateShoppingCartProducts(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = createShoppingCartFromDTO(shoppingCartDTO);
        Set<ShoppingCartProductDTO> shoppingCartProductDTOS = shoppingCartDTO.getShoppingCartProductsDTO();
        for (ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProductDTOS) {

            ObjectMapper mapper = new ObjectMapper();

            Product product = mapper.convertValue(shoppingCartProductDTO.getProductDTO(),
                    new TypeReference<Product>() {});

            ShoppingCartProductPK shoppingCartProductPK = createShoppingCartProductPk(shoppingCart, product);

            ShoppingCartProduct shoppingCartProduct = createShoppingCartProduct(shoppingCartProductPK, shoppingCartProductDTO);

            shoppingCartProductRepository.save(shoppingCartProduct);
        }
    }

    //delete one OrderProduct for an Order
    public void deleteShoppingCartProduct(Long shoppingCartId, Long shoppingCartProductId) {
        shoppingCartProductRepository.deleteByShoppingCartIdAndShoppingCartProductId(shoppingCartId, shoppingCartProductId);
    }
}
