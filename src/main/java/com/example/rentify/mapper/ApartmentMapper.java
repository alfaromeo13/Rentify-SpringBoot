package com.example.rentify.mapper;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.enums.AttributeValueEnum;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(target = "apartmentAttributes.attributeValue",
            defaultValue = "getAttributeValue(apartment.apartmentAttributes.attributeValue)")
    Apartment toEntity(ApartmentDTO apartment);
    //'target' is field in Apartment class while 'defaultValue' is custom expression which assigns value to target field.

    List<ApartmentDTO> toDTOList(List<Apartment> apartmentList);

    @BeanMapping
    default String getAttributeValue(AttributeValueEnum attributeValueEnum) {
        return attributeValueEnum.getValue();
    }
    //we define a custom mapping function to transform the value of a attributeValue field
    //of ApartmentDTO to the corresponding AttributeValueEnum during the mapping process.
}