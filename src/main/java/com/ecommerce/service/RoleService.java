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
public class RoleService implements AbmService<RoleDTO>{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(RoleDTO roleDTO){
        Role role = new Role();
        role.setName(roleDTO.getName());

        roleRepository.save(role);
    }

    @Override
    public List<RoleDTO> findAll(){
        return roleRepository.findAllRoles();
    }

    @Override
    public void update(RoleDTO entity) {
        //repository.updateROle();
    }

    @Override
    public RoleDTO findById(Long roleId){
        return roleRepository.findRoleDTOById(roleId);
    }

    @Override
    public void delete(Long id){
        roleRepository.deleteById(id);
    }
}
