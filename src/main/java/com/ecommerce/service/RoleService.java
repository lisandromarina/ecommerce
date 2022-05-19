package com.ecommerce.service;

import com.ecommerce.DTO.RoleDTO;
import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void createRole(RoleDTO roleDTO){
        Role role = new Role();
        role.setName(roleDTO.getName());

        roleRepository.save(role);
    }

    public List<RoleDTO> findAllRoles(){
        return roleRepository.findAllRoles();
    }

    public RoleDTO findRoleDTOByid(Long roleId){
        return roleRepository.findRoleDTOById(roleId);
    }

    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }
}
