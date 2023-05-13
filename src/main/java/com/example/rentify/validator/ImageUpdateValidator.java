package com.example.rentify.validator;

import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.projections.ImageProjection;
import com.example.rentify.repository.ImageRepository;
import com.example.rentify.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ImageUpdateValidator implements Validator {

    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final ImageInsertValidator imageInsertValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(IncomingImagesDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IncomingImagesDTO images = (IncomingImagesDTO) target;
        imageInsertValidator.validateImages(errors, images);
        validateNumAndSize(errors, images);
    }

    @SneakyThrows
    private void validateNumAndSize(Errors errors, IncomingImagesDTO incomingImages) {
        ImageProjection imageData = imageRepository.numAndSum(incomingImages.getApartmentId());
        if (imageData != null) { //if this apartment already has some images
            if (imageData.getNumber() + incomingImages.getImages().length > 10) {
                errors.rejectValue("images", "images.error", "Maximum is 10 images per apartment!");
                throw new ValidationException("Validation error", errors);
            }
            double sum = Arrays.stream(incomingImages.getImages())
                    .mapToDouble(imageService::calculateSizeInMB).sum();
            if (imageData.getSize() + sum > 100) throw new MaxUploadSizeExceededException(104857600);
        }
    }
}