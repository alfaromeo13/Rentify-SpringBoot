package com.example.rentify.mapper;

import com.example.rentify.dto.AttributeDTO;
import com.example.rentify.entity.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    Attribute toEntity(AttributeDTO attributeDTO);
    List<AttributeDTO> toDTOList(List<Attribute> attribute);
}
