package com.example.rentify.service;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.entity.Role;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public void save(RoleDTO roleDTO) {
        roleRepository.save(roleMapper.toEntity(roleDTO));
    }

    public Boolean update(Integer id, RoleDTO roleDTO) {
        boolean roleExists = roleRepository.existsById(id);
        if (roleExists) {
            roleDTO.setId(id);
            save(roleDTO);//method from above
            return true;
        } else return false;
    }
}