package com.ecommerce.service.impl;

import com.ecommerce.DTO.RoleDTO;
import com.ecommerce.exception.ApiRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.service.AbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements AbmService<RoleDTO> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(RoleDTO roleDTO) {
        validateRoleFields(roleDTO);

        Role role = new Role();
        role.setName(roleDTO.getName());

        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public List<RoleDTO> findAll() {
        try {
            return roleRepository.findAllRoles();
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void update(RoleDTO entity) {
        //repository.updateROle();
    }

    @Override
    public RoleDTO findById(Long roleId) {
        validateRoleExist(roleId);
        try {
            return roleRepository.findRoleDTOById(roleId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long roleId) {
        validateRoleExist(roleId);
        try {
            roleRepository.deleteById(roleId);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage(), e);
        }
    }

    private void validateRoleFields(RoleDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
            throw new ApiRequestException("The Role cannot have empty fields", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRoleExist(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ApiRequestException("The Role with id: " + roleId + " doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
