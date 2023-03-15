package com.example.rentify.mapper;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDTO roleDTO);
}
