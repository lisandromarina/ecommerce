package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.User;
import com.ecommerce.repository.ShoppingCartProductRepository;
import com.ecommerce.repository.ShoppingCartRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    UserRepository userRepository;

    //Save an order in the database
    @Override
    public void create(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDateCreated(LocalDate.now());

        User user = userRepository.findById(userId).orElseThrow();
        shoppingCart.setUser(user);

        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    //return order by id
    @Override
    public ShoppingCartDTO findById(Long id) {
        return shoppingCartRepository.findShoppingCartDTOById(id);
    }

    //Delete order by orderId
    @Override
    public void delete(Long orderId){
        shoppingCartProductRepository.deleteAllByShoppingCartId(orderId);
        shoppingCartRepository.deleteById(orderId);
    }
}
