package com.ecommerce.repository;

import com.ecommerce.DTO.RoleDTO;
import com.ecommerce.DTO.ShoppingCartProductDTO;
import com.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(
            "SELECT new com.ecommerce.DTO.RoleDTO(" +
                    "r.id, " +
                    "r.name) " +
                    "FROM Role r "
    )
    List<RoleDTO> findAllRoles();

    @Query(
            "SELECT new com.ecommerce.DTO.RoleDTO(" +
                    "r.id, " +
                    "r.name) " +
                    "FROM Role r " +
                    "WHERE r.id = :roleId"
    )
    RoleDTO findRoleDTOById(@Param("roleId") Long roleId);
}
