package com.example.rentify.mapper;

import com.example.rentify.dto.RentalApartmentDTO;
import com.example.rentify.dto.RentalDTO;
import com.example.rentify.dto.RentalSearchDTO;
import com.example.rentify.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    Rental toEntity(RentalDTO rentalDTO);

    Rental toEntity(RentalSearchDTO rentalDTO);

    List<RentalDTO> toDTOList(List<Rental> rentals);

    @Mapping(source = "apartment.id", target = "apartmentId")
    RentalApartmentDTO toRentalDTO(Rental rental);
}
