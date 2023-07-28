package com.example.rentify.validator;

import com.example.rentify.dto.*;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.AttributeService;
import com.example.rentify.service.NeighborhoodService;
import com.example.rentify.service.PeriodService;
import com.example.rentify.service.PropertyTypeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApartmentValidator implements Validator {

    private final PeriodService periodService;
    private final AttributeService attributeService;
    private final NeighborhoodService neighborhoodService;
    private final PropertyTypeService propertyTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ApartmentDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ApartmentDTO apartment = (ApartmentDTO) target;
        validate(apartment, errors);
    }

    @SneakyThrows
    private void validate(ApartmentDTO apartment, Errors errors) {
        validateId(apartment.getId(), errors);
        validateTitle(apartment.getTitle(), errors);
        validatePrice(apartment.getPrice(), errors);
        validateSqMeters(apartment.getSqMeters(), errors);
        validateUser(apartment.getUser(), errors);
        validateDescription(apartment.getDescription(), errors);
        validateNumOfBedrooms(apartment.getNumOfBedrooms(), errors);
        validateNumOfBathrooms(apartment.getNumOfBedrooms(), errors);
        validatePeriod(apartment.getPeriod(), errors);
        validateType(apartment.getPropertyType(), errors);
        validatePhoneNumber(apartment.getNumber(), errors);
        validateAddress(apartment.getAddress(), errors);
        validateImages(apartment.getImages(), errors);
        validateAttributes(apartment.getApartmentAttributes(), errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
    }

    private void validateType(PropertyTypeDTO propertyType, Errors errors) {
        if (propertyType == null)
            errors.rejectValue("propertyType", "type.required", "Property type is missing!");
        else if (!propertyTypeService.existsByName(propertyType.getName()))
            errors.rejectValue("propertyType", "type.error", "Invalid property type value!");
    }

    private void validateId(Integer id, Errors errors) {
        if (id != null) errors.rejectValue("id", "id.error", "Id shouldn't be sent!");
    }

    private void validateImages(Set<ImageDTO> images, Errors errors) {
        if (images != null)
            errors.rejectValue("images", "images.error", "Images cant't be sent like that!");
    }

    private void validateAttributes(Set<ApartmentAttributeDTO> apartmentAttributes, Errors errors) {
        if(apartmentAttributes==null) {
            errors.rejectValue(
                    "apartmentAttributes",
                    "apartmentAttributes.error",
                    "Missing apartment attributes!");
            return;
        }
        for (ApartmentAttributeDTO apartmentAttribute : apartmentAttributes) {
            if (!attributeService.existsByName(apartmentAttribute.getAttribute().getName()))
                errors.rejectValue(
                        "attribute.name",
                        "name.error",
                        "Attribute " + apartmentAttribute.getAttribute().getName() + " doesn't exist!");
        }
    }

    private void validateAddress(AddressDTO address, Errors errors) {
        if (address == null) {
            errors.rejectValue("address", "address.required", "Address missing!");
            return;
        }
        if (address.getX() == null || address.getY() == null)
            errors.rejectValue("address.x", "x.required", "Missing property pin on the map");
        if (StringUtils.isBlank(address.getStreet()))
            errors.rejectValue("address.street", "street.required", "Street missing!");
        if(address.getNeighborhood()==null)
            errors.rejectValue("address.neighborhood", "neighborhood.required", "Neighborhood missing!");
        else if(!neighborhoodService.exists(address.getNeighborhood().getId()))
            errors.rejectValue("address.neighborhood.id", "neighborhood.error", "Invalid neighborhood");

    }

    private void validatePrice(Double price, Errors errors) {
        if (price == null) errors.rejectValue("price", "price.required", "Price is missing!");
        else if (price <= 0) errors.rejectValue("price", "price.error", "Price not valid!");
        else {
            String priceString = String.valueOf(price);
            int decimalPlaces = priceString.length() - priceString.indexOf('.') - 1;
            if (decimalPlaces > 1)
                errors.rejectValue(
                        "price",
                        "price.error",
                        "Price should have only one digit after the decimal point!");
        }
    }

    private void validateSqMeters(Integer sqMeters, Errors errors) {
        if (sqMeters == null)
            errors.rejectValue("sqMeters", "sqMeters.required", "SqMeters are missing!");
        else if (sqMeters <= 0)
            errors.rejectValue("sqMeters", "sqMeters.error", "SqMeters not valid!");
    }

    private void validateNumOfBedrooms(Integer numOfBedrooms, Errors errors) {
        if (numOfBedrooms == null)
            errors.rejectValue("numOfBedrooms",
                    "numOfBedrooms.required",
                    "Number of bedrooms is missing!");
        else if (numOfBedrooms <= 0)
            errors.rejectValue("numOfBedrooms",
                    "numOfBedrooms.error",
                    "Number of bedrooms not valid!");
    }

    private void validateNumOfBathrooms(Integer numOfBathrooms, Errors errors) {
        if (numOfBathrooms == null)
            errors.rejectValue("numOfBathrooms",
                    "numOfBathrooms.required",
                    "Number of bathrooms is missing!");
        else if (numOfBathrooms <= 0)
            errors.rejectValue("numOfBathrooms",
                    "numOfBathrooms.error",
                    "Number of bathrooms not valid!");
    }

    private void validateTitle(String title, Errors errors) {
        if (title == null) errors.rejectValue("title", "title.required", "Title is missing!");
    }

    private void validatePhoneNumber(String number, Errors errors) {
        if (number == null)
            errors.rejectValue("number", "number.required", "Number is missing!");
    }

    private void validateDescription(String description, Errors errors) {
        if (description == null)
            errors.rejectValue("description", "description.required", "Description missing!");
        else if(description.length()<122)
            errors.rejectValue(
                    "description",
                    "description.error",
                    "Description should be at least 122 characters long!");
    }

    private void validateUser(UserDTO user, Errors errors) {
        if (user != null) errors.rejectValue("user", "user.error", "User should't be sent!");
    }

    private void validatePeriod(PeriodDTO period, Errors errors) {
        if (period == null)
            errors.rejectValue("period", "period.required", "Period is missing!");
        else if (!periodService.existsByName(period.getName()))
            errors.rejectValue("period", "period.error", "Invalid period value!");
    }
}