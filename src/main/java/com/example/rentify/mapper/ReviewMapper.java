package com.example.rentify.mapper;

import com.example.rentify.dto.ReviewApartmentDTO;
import com.example.rentify.dto.ReviewDTO;
import com.example.rentify.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    List<ReviewDTO> toDTOList(List<Review> reviews);
    Review toEntity(ReviewApartmentDTO reviewApartmentDTO);
}