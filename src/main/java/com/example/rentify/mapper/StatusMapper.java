package com.example.rentify.mapper;

import com.example.rentify.dto.StatusDTO;
import com.example.rentify.entity.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    List<StatusDTO> toDTO(List<Status> status);
}