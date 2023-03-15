package com.example.rentify.service;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void addRole(Integer id, RoleDTO roleDTO) {
        Optional<User> userOptional = userRepository.findWithRolesById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role role = new Role();
            role.setName(roleDTO.getName());
            role.setId(roleDTO.getId());
            user.addRole(role);
            userRepository.save(user);
        }
    }

    public UserWithRolesDTO findWithRoles(Integer id) {
        return userRepository.userWithRolesDTO(id);
    }

    public void store(UserDTO userDTO) {
        User user=userMapper.toEntity(userDTO);
        userRepository.save(user);
    }
}
