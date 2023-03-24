package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.mapper.RoleMapper;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

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

    public Boolean deleteRole(Integer id, RoleDTO roleDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.removeRoleById(roleDTO.getId());
            userRepository.save(user);
            return true;
        } else return false;
    }

    public UserWithRolesDTO findWithRoles(Integer id) {
        User user = userRepository.findUserWithRoles(id);
        return userMapper.toDTO(user);
    }

    @CacheEvict(value = "favourite", allEntries = true)
    //izbrisi i favourite i sve sto ima ukesirano
    public Boolean delete(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        } else return false;
    }

    @CacheEvict(value = "favourite", allEntries = true)
    public boolean addFavourites(Integer userId, Integer apartmentId) {
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
    public Boolean deleteFavApartment(Integer userId, Integer apartmentId) {
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
}