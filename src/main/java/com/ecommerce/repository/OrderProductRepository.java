package com.ecommerce.repository;

import com.ecommerce.DTO.OrderProductDTO;
import com.ecommerce.model.OrderProduct;
import com.ecommerce.model.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {

    @Query(
            "SELECT new com.ecommerce.DTO.OrderProductDTO(" +
                    "p.id, " +
                    "p.name, " +
                    "p.price," +
                    "op.sellPrice, " +
                    "op.quantity) " +
                    "FROM OrderProduct op " +
                    "INNER JOIN Product p ON (op.pk.product.id = p.id) " +
                    "WHERE op.pk.order.id = :orderId"
    )
    Set<OrderProductDTO> findOrderProductByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Query(
            "DELETE " +
                    "FROM OrderProduct op " +
                    "WHERE op.pk.order.id = :orderId " +
                    "AND op.pk.product.id = :productId"
    )
    void deleteByOrderIdAndOrderProductId(@Param("orderId") Long orderId,
                                          @Param("productId") Long productId);
}
