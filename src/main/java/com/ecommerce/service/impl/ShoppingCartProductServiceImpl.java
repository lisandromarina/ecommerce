package com.ecommerce.service.impl;

import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.ShoppingCartProduct;
import com.ecommerce.model.ShoppingCartProductPK;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.service.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    //update ShoppingCartProducts for a specific shoppingCart
    @Override
    public void update(ShoppingCartProductDTO shoppingCartProductDTO) {
        validateShoppingCartProductFields(shoppingCartProductDTO);

        Long idShoppingCart = shoppingCartProductDTO.getIdShoppingCart();
        Long idProduct = shoppingCartProductDTO.getIdProduct();
        ShoppingCartProductPK shoppingCartProductPK = createShoppingCartProductPk(idShoppingCart, idProduct);

        ShoppingCartProduct shoppingCartProduct = createShoppingCartProduct(shoppingCartProductPK, shoppingCartProductDTO);

        try {
            shoppingCartProductRepository.save(shoppingCartProduct);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    //delete one ShoppingCartProduct for an ShoppingCart
    @Override
    public void delete(Long shoppingCartId, Long productId) {
        try {
            shoppingCartProductRepository
                    .deleteByShoppingCartIdAndProductId(shoppingCartId, productId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    //return all orderProducts for an Order
    @Override
    public Set<ShoppingCartProductDTO> findByShoppingCartId(Long idShoppingCart) {
        validateShoppingCartId(idShoppingCart);
        return shoppingCartProductRepository.findShoppingCartProductByShoppingCartId(idShoppingCart);
    }

    private void validateShoppingCartProductFields(ShoppingCartProductDTO shoppingCartProductDTO) {
        Long idShoppingCart = shoppingCartProductDTO.getIdShoppingCart();
        Long idProduct = shoppingCartProductDTO.getIdProduct();
        Integer quantity = shoppingCartProductDTO.getQuantity();
        try {
            if (!productRepository.existsById(idProduct)) {
                throw new ApiRequestException("Cannot create ShoppingCartProduct, Product with id: " + idProduct + " doesn't exist",
                        HttpStatus.NOT_FOUND);
            } else if (!shoppingCartRepository.existsById(idShoppingCart)) {
                throw new ApiRequestException("Cannot create ShoppingCartProduct, Shopping Cart with id: " + idShoppingCart + " doesn't exist",
                        HttpStatus.NOT_FOUND);
            } else if (quantity <= 0) {
                throw new ApiRequestException("Quantity cannot be 0",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateShoppingCartId(Long idShoppingCart) {
        try {
            if (!shoppingCartRepository.existsById(idShoppingCart)) {
                throw new ApiRequestException("Shopping Cart with id: " + idShoppingCart + " doesn't exist",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    //Create an ShoppingCartProductPk
    private ShoppingCartProductPK createShoppingCartProductPk(Long idShoppingCart, Long idProduct) {
        ShoppingCartProductPK shoppingCartProductPK = new ShoppingCartProductPK();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(idShoppingCart);

        Product product = new Product();
        product.setId(idProduct);

        shoppingCartProductPK.setOrder(shoppingCart);
        shoppingCartProductPK.setProduct(product);

        return shoppingCartProductPK;
    }

    //Create ShoppingCartProduct from a ShoppingCartProductDTO
    private ShoppingCartProduct createShoppingCartProduct(ShoppingCartProductPK shoppingCartProductPK,
                                                          ShoppingCartProductDTO shoppingCartProductDTO) {

        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
        shoppingCartProduct.setPk(shoppingCartProductPK);
        shoppingCartProduct.setQuantity(shoppingCartProductDTO.getQuantity());
        try {
            Double sellPrice = productRepository.findPriceById(shoppingCartProductPK.getProduct().getId());
            shoppingCartProduct.setSellPrice(sellPrice);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }

        return shoppingCartProduct;
    }
}
