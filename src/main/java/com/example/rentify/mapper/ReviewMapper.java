package com.example.rentify.mapper;

import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewDTO reviewDTO);
}
