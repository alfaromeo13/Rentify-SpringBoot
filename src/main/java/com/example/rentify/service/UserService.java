package com.example.rentify.service;

import com.example.rentify.dto.*;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Message;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.mapper.FullUserMapper;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final FullUserMapper fullUserMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ApartmentMapper apartmentMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApartmentRepository apartmentRepository;

    @Cacheable(value = "user", key = "#id")
    public FullUserDTO find(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return fullUserMapper.toFullDTO(user);
        } else return new FullUserDTO();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

    @CachePut(value = "user", key = "#id")
    public Boolean update(Integer id, UserDTO userDTO) {
        boolean userExists = userRepository.existsById(id);
        if (userExists) {
            userDTO.setId(id);
            save(userDTO);
            return true;
        } else return false;
    }

    @CacheEvict(value = "user", key = "#id")
    public Boolean delete(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @CachePut(value = "user-roles", key = "#id")
    public Boolean addRole(Integer id, RoleDTO roleDTO) { //id is user's id
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleMapper.toEntity(roleDTO);
            user.addRole(role);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @CachePut(value = "user-roles", key = "#id")
    public Boolean deleteRoleForUser(Integer id, RoleDTO roleDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.removeRoleById(roleDTO.getId());
            userRepository.save(user);
            return true;
        } else return false;
    }

    @Cacheable(value = "user-roles", key = "#id")
    public UserWithRolesDTO findWithRoles(Integer id) {
        return userMapper.toDTO(userRepository.findUserWithRoles(id));
    }

    @CacheEvict(value = "favourite", allEntries = true)
    public Boolean addFavourites(Integer userId, Integer apartmentId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
            user.addFavouriteApartment(apartment);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @CacheEvict(value = "favourite", allEntries = true)
    public Boolean deleteFavourites(Integer userId, Integer apartmentId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.removeFavouriteApartmentById(apartmentId);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @Cacheable(value = "favourite", key = "#id + ':' + #pageable.toString()")
    public List<ApartmentDTO> findFavouriteApartmentsForUserId(Integer id, Pageable pageable) {
        Page<Apartment> apartmentsPage = userRepository.favouriteApartmentsForUserById(id, pageable);
        return apartmentsPage.hasContent() ?
                apartmentMapper.toDTOList(apartmentsPage.getContent()) :
                Collections.emptyList();
    }

    @Cacheable(value = "users", key = "#pageable.toString()")
    public List<UserDTO> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.hasContent() ?
                userMapper.toDTOList(usersPage.getContent()) :
                Collections.emptyList();
    }

    public void register(UserCreateDTO userCreateDTO) {
        //Posto smo napravili bean PasswordEncodera u klasi SecurityConfiguration,ovdje ga injectujemo i
        //i pozivamo njegovu metodu encode() koja ce izgenerisati proslijedjeni password u BCrypt formatu
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        User user = userMapper.createUserToEntity(userCreateDTO);
        Role role = roleRepository.findRoleByName("registered user");
        user.addRole(role);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}