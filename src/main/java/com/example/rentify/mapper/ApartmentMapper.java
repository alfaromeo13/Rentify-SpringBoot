package com.example.rentify.mapper;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
    ApartmentDTO toDTO(Apartment apartment);
    Apartment toEntity(ApartmentDTO apartment);
    List<ApartmentDTO> toDTOList(List<Apartment> apartmentList);
}