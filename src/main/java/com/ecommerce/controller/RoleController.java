package com.ecommerce.controller;

import com.ecommerce.DTO.RoleDTO;
import com.ecommerce.DTO.UserDTO;
import com.ecommerce.service.RoleService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/save")
    public void createRole(@RequestBody RoleDTO roleDTO){
        roleService.save(roleDTO);
    }

    @PutMapping("/update")
    public void updateRole(RoleDTO roleDTO){
        //userService.update(userDTO);
    }

    @GetMapping("/findAll")
    public List<RoleDTO> findAllRole(){
        return roleService.findAll();
    }

    @GetMapping("/findById/{roleId}")
    public RoleDTO findRoleDTOById(@PathVariable ("roleId") Long roleId){
        return roleService.findById(roleId);
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
    }

}
