package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.User;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    UserRepository userRepository;

    //Save an order in the database
    public ShoppingCart createShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDateCreated(LocalDate.now());

        User user = userRepository.findById(userId).orElseThrow();
        shoppingCart.setUser(user);

        return shoppingCartRepository.save(shoppingCart);
    }

    public void updateShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    //return order by id
    public ShoppingCartDTO findShoppingCartDTOById(Long id) {
        return shoppingCartRepository.findShoppingCartDTOById(id);
    }

    //Delete order by orderId
    public void deleteShoppingCartById(Long orderId){
        shoppingCartProductRepository.deleteAllByShoppingCartId(orderId);
        shoppingCartRepository.deleteById(orderId);
    }
}
