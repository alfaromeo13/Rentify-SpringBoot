package com.example.rentify.mapper;

import com.example.rentify.dto.PropertyTypeDTO;
import com.example.rentify.entity.PropertyType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyTypeMapper {
    PropertyTypeDTO toDTO(PropertyType propertyType);
}