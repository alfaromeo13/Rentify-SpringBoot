package com.example.rentify.mapper;

import com.example.rentify.dto.ImageDTO;
import com.example.rentify.entity.Image;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toEntity(ImageDTO image);
    Set<ImageDTO> toDTOList(List<Image> images);
}