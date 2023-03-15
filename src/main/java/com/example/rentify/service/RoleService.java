package com.example.rentify.service;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.dto.RoleDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.entity.Role;
import com.example.rentify.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        roleRepository.save(role);
    }

    public Boolean update(Integer id, RoleDTO roleDTO) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setId(roleDTO.getId());
            role.setName(roleDTO.getName());
            roleRepository.save(role);
            return true;
        } else return false;
    }

}
