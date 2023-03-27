package com.example.rentify.mapper;

import com.example.rentify.dto.RentalDTO;
import com.example.rentify.entity.Rental;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    Rental toEntity(RentalDTO rentalDTO);
    List<RentalDTO> toDTOList(List<Rental> rentals);
}
