package com.example.rentify.mapper;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
}
