package com.example.rentify.mapper;

import com.example.rentify.dto.ApartmentAttributeDTO;
import com.example.rentify.entity.ApartmentAttribute;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ApartmentAttributeMapper {
    Set<ApartmentAttribute> toEntityList(Set<ApartmentAttributeDTO> apartmentAttributeDTO);
}
