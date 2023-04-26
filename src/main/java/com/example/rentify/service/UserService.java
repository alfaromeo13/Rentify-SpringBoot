package com.example.rentify.service;

import com.example.rentify.dto.*;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApartmentRepository apartmentRepository;

    public UserDTO find() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        return userMapper.toDTO(user);
    }

    public void update(UserDTO userDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        User updatedUser = userMapper.toEntity(userDTO);
        updatedUser.setId(user.getId());
        updatedUser.setPassword(user.getPassword());
        userRepository.save(userMapper.toEntity(userDTO));
    }

    public Boolean delete(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        } else return false;
    }

    public Boolean addFavourites(Integer apartmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Optional<Apartment> apartment = apartmentRepository.findById(apartmentId);
        if (apartment.isPresent()) {
            user.addFavouriteApartment(apartment.get());
            userRepository.save(user);
            return true;
        } else return false;
    }

    public void deleteFavourites(Integer apartmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        user.removeFavouriteApartmentById(apartmentId);
        userRepository.save(user);
    }

    public List<Integer> getFavourites(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Integer> apartmentsPage = userRepository.favouriteApartmentsForUser(username, pageable);
        return apartmentsPage.hasContent() ? apartmentsPage.getContent() : Collections.emptyList();
    }

    public List<UserDTO> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.hasContent() ? userMapper.toDTOList(usersPage.getContent()) : Collections.emptyList();
    }

    public void register(UserCreateDTO userCreateDTO) {
        //Posto smo napravili bean PasswordEncodera u klasi SecurityConfiguration,ovdje ga injectujemo i
        //i pozivamo njegovu metodu encode() koja ce izgenerisati proslijedjeni password u BCrypt formatu
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        User user = userMapper.createUserToEntity(userCreateDTO);
        Role role = roleRepository.findRoleByName("ROLE_REGISTERED");
        user.addRole(role);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}