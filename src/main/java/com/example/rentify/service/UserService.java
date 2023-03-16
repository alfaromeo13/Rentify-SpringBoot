package com.example.rentify.service;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

    public boolean addRole(Integer id, RoleDTO roleDTO) { //id is user's id
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleMapper.toEntity(roleDTO);
            user.addRole(role);
            userRepository.save(user);
            return true;
        } else return false;
    }

    public UserWithRolesDTO findWithRoles(Integer id) {
        User user = userRepository.userWithRoles(id);
        return userMapper.toDTO(user);
    }
}