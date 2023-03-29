package com.example.rentify.mapper;

import com.example.rentify.dto.FullUserDTO;
import com.example.rentify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FullUserMapper {
    FullUserDTO toFullDTO(User user);
}
