package com.example.rentify.mapper;

import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.entity.Rental;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalSearchMapper {
    List<RentalSearchDTO> toDTOList(List<Rental> rentals);
}
