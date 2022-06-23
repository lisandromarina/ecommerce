package com.ecommerce.service.impl;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.*;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    //create ShoppingCartProducts for a specific shoppingCart
    @Override
    public void createOrUpdate(ShoppingCartProductDTO shoppingCartProductDTO) {
        validateShoppingCartProductFields(shoppingCartProductDTO);

        try {
            Long userId = shoppingCartProductDTO.getUserId();
            ShoppingCartDTO shoppingCartDTO = shoppingCartRepository.findByUserId(userId);

            Long idShoppingCart;
            if (shoppingCartDTO == null) {
                idShoppingCart = createShoppingCart(userId);
            } else {
                idShoppingCart = shoppingCartDTO.getId();
            }

            createShoppingCartProduct(shoppingCartProductDTO, idShoppingCart);
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

            if(shoppingCartProductRepository.isEmptyShoppingCart(shoppingCartId)){
                shoppingCartRepository.deleteById(shoppingCartId);
            }
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

    private void createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO, Long idShoppingCart) {
        Long idProduct = shoppingCartProductDTO.getIdProduct();
        ShoppingCartProductPK shoppingCartProductPK = createShoppingCartProductPk(idShoppingCart, idProduct);
        //VALIDAR SI EL CARRITO NO TIENE PRODUCTOS, LO ELIMINO
        ShoppingCartProduct shoppingCartProduct = createShoppingCartProduct(shoppingCartProductPK, shoppingCartProductDTO);

        try {
            shoppingCartProductRepository.save(shoppingCartProduct);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private Long createShoppingCart(Long userId) {
        User user = getUserById(userId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDateCreated(LocalDate.now());
        shoppingCart.setComplete(false);

        try {
            shoppingCart.setUser(user);
            return shoppingCartRepository.save(shoppingCart).getId();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateShoppingCartProductFields(ShoppingCartProductDTO shoppingCartProductDTO) {
        Long idUser = shoppingCartProductDTO.getUserId();
        Long idProduct = shoppingCartProductDTO.getIdProduct();
        Integer quantity = shoppingCartProductDTO.getQuantity();
        try {
            if (!productRepository.existsById(idProduct)) {
                throw new ApiRequestException("Cannot create ShoppingCartProduct, Product with id: " + idProduct + " doesn't exist",
                        HttpStatus.NOT_FOUND);
            } else if (!userRepository.existsById(idUser)) {
                throw new ApiRequestException("Cannot create ShoppingCartProduct, User with id: " + idUser + " doesn't exist",
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

    private User getUserById(Long idUser) {
        Optional<User> user;
        try {
            user = userRepository.findById(idUser);
            if (!user.isPresent()) {
                throw new ApiRequestException("Cannot create user, role with id: " + idUser + " not found", HttpStatus.NOT_FOUND);
            }
            return user.get();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }
}
