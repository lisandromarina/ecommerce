package com.ecommerce.repository;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.OrderDTO(" +
                    "o.id, " +
                    "o.dateCreated, " +
                    "o.status) " +
                    "FROM Order o " +
                    "WHERE o.id = :id"
    )
    OrderDTO findOrderDTOById(@Param("id") Long id);
}
