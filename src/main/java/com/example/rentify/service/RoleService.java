package com.example.rentify.service;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public List<String> find() {
        return roleRepository.findAllNamesJPQL();
    }

    public void save(RoleDTO roleDTO) {
        roleRepository.save(roleMapper.toEntity(roleDTO));
    }

    public Boolean update(Integer id, RoleDTO roleDTO) {
        boolean roleExists = roleRepository.existsById(id);
        if (roleExists) {
            roleDTO.setId(id);
            save(roleDTO); //method from above
            return true;
        } else return false;
    }

    public Boolean delete(Integer id) {
        boolean roleExists = roleRepository.existsById(id);
        if (roleExists) {
            roleRepository.deleteById(id);
            return true;
        } else return false;
    }
}