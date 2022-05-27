package com.ecommerce.service;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.model.ShoppingCartProduct;
import com.ecommerce.model.ShoppingCartProductPK;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ShoppingCartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartProductService {

    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    @Autowired
    ProductRepository productRepository;

    //Create an ShoppingCart from ShoppingCartDTO
    private ShoppingCart createShoppingCartFromDTO(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDTO.getId());
        return shoppingCart;
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

        Double sellPrice = productRepository.findPriceById(shoppingCartProductPK.getProduct().getId());
        shoppingCartProduct.setSellPrice(sellPrice);

        return shoppingCartProduct;
    }

    //update ShoppingCartProducts for a specific shoppingCart
    public void updateShoppingCartProducts(ShoppingCartProductDTO shoppingCartProductDTO) {
            Long idShoppingCart = shoppingCartProductDTO.getIdShoppingCart();
            Long idProduct = shoppingCartProductDTO.getIdProduct();

            ShoppingCartProductPK shoppingCartProductPK = createShoppingCartProductPk(idShoppingCart, idProduct);

            ShoppingCartProduct shoppingCartProduct = createShoppingCartProduct(shoppingCartProductPK, shoppingCartProductDTO);

            shoppingCartProductRepository.save(shoppingCartProduct);

    }

    //delete one ShoppingCartProduct for an ShoppingCart
    public void deleteShoppingCartProduct(Long shoppingCartId, Long productId) {
        shoppingCartProductRepository
                .deleteByShoppingCartIdAndShoppingCartProductId(shoppingCartId, productId);
    }

    //return all orderProducts for an Order
    public Set<ShoppingCartProductDTO> findShoppingCartProductsByShoppingCartId(Long id) {
        return shoppingCartProductRepository.findShoppingCartProductByShoppingCartId(id);
    }
}
