package com.ecommerce.service.impl;

import com.ecommerce.DTO.*;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.model.Shipping;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.User;
import com.ecommerce.repository.*;
import com.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShippingRepository shippingRepository;

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

            Double totalPrice = shoppingCartProductRepository.getTotalPriceByShoppingCartId(id);
            shoppingCartDTO.setTotalPrice(totalPrice);
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
                Double totalPrice = shoppingCartProductRepository.getTotalPriceByShoppingCartId(shoppingCartDTO.getId());
                shoppingCartDTO.setTotalPrice(totalPrice);
            }
            return shoppingCartDTO;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public ShoppingCartDTO checkoutShoppingCart(Principal principal) {
        try{
            UserDTO user = userRepository.findUserByUsername(principal.getName());
            //get user address
            AddressDTO address = addressRepository.findActiveAddressByUserId(user.getId());

            ShoppingCartDTO shoppingCart = findByUserId(user.getId());

            //get all the sellers for the cart
            List<Long> sellers = getSellersFromShoppingCart(shoppingCart.getShoppingCartProductsDTO());

            //create shipping model for each sellers
            for (Long sellerId : sellers) {
                Shipping shipping = new Shipping();
                shipping.setLocation(address.getLocation());
                shipping.setProvince(address.getProvince());
                shipping.setCountry(address.getCountry());
                shipping.setPostalCode(address.getPostalCode());
                shipping.setStreet(address.getStreet());
                shipping.setStreetNumber(address.getStreetNumber());
                shipping.setDepartment(address.getDepartment());

                shipping.setSeller(new User(sellerId));
                shipping.setShoppingCart(new ShoppingCart(shoppingCart.getId()));
                shipping.setDateCreated(LocalDate.now());

                shipping.setType("ANDREANI");
                shipping.setStatus("Esperando al vendedor");

                shippingRepository.save(shipping);
            }
                //change cart status to complete
                shoppingCartRepository.completeShoppingCart(shoppingCart.getId());
                //advice each seller, by email, that they make a sell and they have to deliver the package
            return null;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private List<Long> getSellersFromShoppingCart(Set<ShoppingCartProductDTO> shoppingCartProduct) {
        List<UserDTO> sellers = new ArrayList<>();
        List<ProductDTO> products = getAllProductForTheShoppingCart(shoppingCartProduct);

        sortByUserIdForEachProduct(products);
        return getUserIdNotRepeated(products);
    }

    private List<Long> getUserIdNotRepeated(List<ProductDTO> products){
        return products.stream()
                .map(ProductDTO::getUserId)
                .distinct()
                .collect(Collectors.toList());

    }

    private List<ProductDTO> getAllProductForTheShoppingCart(Set<ShoppingCartProductDTO> shoppingCartProduct){
        List<ProductDTO> products = new ArrayList<>();
        for (ShoppingCartProductDTO product : shoppingCartProduct) {
            try {
                products.add(productRepository.findProductDTOById(product.getIdProduct()));
            } catch (Exception e) {
                throw new ApiRequestException(e.getMessage(), e);
            }
        }
        return products;
    }

    private static void sortByUserIdForEachProduct(List<ProductDTO> products) {
        Collections.sort(products, new Comparator<ProductDTO>() {
            @Override
            public int compare(ProductDTO p1, ProductDTO p2) {
                return p1.getUserId().compareTo(p2.getUserId());
            }
        });
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
