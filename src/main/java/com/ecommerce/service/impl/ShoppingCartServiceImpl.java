package com.ecommerce.service.impl;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.User;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    //return order by id
    @Override
    public ShoppingCartDTO findById(Long id) {
        validateShoppingCartExist(id);
        try{
            ShoppingCartDTO shoppingCartDTO = shoppingCartRepository.findShoppingCartDTOById(id);

            Set<ShoppingCartProductDTO> shoppingCartProductDTO = shoppingCartProductRepository
                    .findShoppingCartProductByShoppingCartId(shoppingCartDTO.getId());
            shoppingCartDTO.setShoppingCartProductsDTO(shoppingCartProductDTO);

            return shoppingCartDTO;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    //Delete order by orderId
    public void delete(Long orderId) {
        try {
            shoppingCartProductRepository.deleteAllByShoppingCartId(orderId);
            shoppingCartRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public ShoppingCartDTO findByUserId(Long userId){
        try{
            ShoppingCartDTO shoppingCartDTO = shoppingCartRepository.findByUserId(userId);

            if(shoppingCartDTO != null){
                Set<ShoppingCartProductDTO> shoppingCartProductDTO = shoppingCartProductRepository
                        .findShoppingCartProductByShoppingCartId(shoppingCartDTO.getId());

                shoppingCartDTO.setShoppingCartProductsDTO(shoppingCartProductDTO);
            }
            return shoppingCartDTO;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiRequestException("Cannot create Shopping Cart, User with id: " + userId + " doesn't exist", HttpStatus.NOT_FOUND);
        }
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

    private void validateShoppingCartExist(Long id) {
        if (!shoppingCartRepository.existsById(id)) {
            throw new ApiRequestException("Shopping Cart with id: " + id + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
