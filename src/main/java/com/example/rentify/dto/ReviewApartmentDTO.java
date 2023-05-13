package com.example.rentify.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewApartmentDTO extends ReviewDTO {
    private Integer apartmentId;
}
