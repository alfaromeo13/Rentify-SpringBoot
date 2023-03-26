package com.example.rentify.mapper;

import com.example.rentify.dto.NeighborhoodDTO;
import com.example.rentify.entity.Neighborhood;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NeighborhoodMapper {
    List<NeighborhoodDTO> toDTOList(List<Neighborhood> neighborhoods);
}