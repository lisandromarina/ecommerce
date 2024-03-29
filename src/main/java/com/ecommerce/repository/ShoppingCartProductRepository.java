package com.ecommerce.repository;

import com.ecommerce.DTO.ShoppingCartDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.ShoppingCartProduct;
import com.ecommerce.model.ShoppingCartProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, ShoppingCartProductPK> {

    @Query(
            "SELECT new com.ecommerce.DTO.ShoppingCartProductDTO(" +
                    "p.id, " +
                    "p.name," +
                    "scp.sellPrice, " +
                    "scp.quantity," +
                    "p.imageUrl) " +
                    "FROM ShoppingCartProduct scp " +
                    "INNER JOIN Product p ON (scp.pk.product.id = p.id) " +
                    "WHERE scp.pk.shoppingCart.id = :shoppingCartId"
    )
    Set<ShoppingCartProductDTO> findShoppingCartProductByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);

    @Query(
            "SELECT SUM(quantity * sell_price) AS totalPrice " +
                    "FROM ShoppingCartProduct scp " +
                    "WHERE scp.pk.shoppingCart.id = :id"
    )
    Double getTotalPriceByShoppingCartId(@Param("id") Long id);

    @Modifying
    @Query(
            "DELETE " +
                    "FROM ShoppingCartProduct scp " +
                    "WHERE scp.pk.shoppingCart.id = :shoppingCartId " +
                    "AND scp.pk.product.id = :productId"
    )
    void deleteByShoppingCartIdAndProductId(@Param("shoppingCartId") Long shoppingCartId,
                                                        @Param("productId") Long productId);

    @Modifying
    @Query(
            "DELETE " +
                    "FROM ShoppingCartProduct scp " +
                    "WHERE scp.pk.shoppingCart.id = :shoppingCartId "
    )
    void deleteAllByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);

    @Query(
            "SELECT CASE WHEN COUNT(scp) <= 0 THEN true ELSE false END " +
                    "FROM ShoppingCartProduct scp WHERE scp.pk.shoppingCart.id = :shoppingCartId"
    )
    Boolean isEmptyShoppingCart(@Param("shoppingCartId") Long shoppingCartId);

}
