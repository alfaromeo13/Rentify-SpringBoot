package com.example.rentify.mapper;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserWithRolesDTO toDTO(User user);
    List<UserDTO> toDTOList(List<User> users);
}
