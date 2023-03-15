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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void addRole(Integer id, RoleDTO roleDTO) {
        //id is user's id
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleMapper.toEntity(roleDTO);
            user.addRole(role);
            userRepository.save(user);
        } else throw new EntityNotFoundException("User with id " + id + " not found!");
    }

    public UserWithRolesDTO findWithRoles(Integer id) {
        return userRepository.userWithRolesDTO(id);
    }

    public void store(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }
}
