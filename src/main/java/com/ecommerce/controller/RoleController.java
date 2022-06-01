package com.ecommerce.controller;

import com.ecommerce.DTO.RoleDTO;
import com.ecommerce.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @PostMapping("/save")
    public void createRole(@RequestBody RoleDTO roleDTO){
        roleServiceImpl.save(roleDTO);
    }

    @PutMapping("/update")
    public void updateRole(RoleDTO roleDTO){
        //userServiceImpl.update(userDTO);
    }

    @GetMapping("/findAll")
    public List<RoleDTO> findAllRole(){
        return roleServiceImpl.findAll();
    }

    @GetMapping("/findById/{roleId}")
    public RoleDTO findRoleDTOById(@PathVariable ("roleId") Long roleId){
        return roleServiceImpl.findById(roleId);
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleServiceImpl.delete(roleId);
    }

}
