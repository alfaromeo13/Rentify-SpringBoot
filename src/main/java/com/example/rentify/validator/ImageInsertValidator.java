package com.example.rentify.validator;

import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImageInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(IncomingImagesDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IncomingImagesDTO images = (IncomingImagesDTO) target;
        validateImages(errors, images);
    }

    public void validateImages(Errors errors, IncomingImagesDTO images) {
        validateNumberOfImages(images,errors);
        for (MultipartFile file : images.getImages()) isPicture(file, errors);
    }

    private Optional<String> getExtension(String filename) {
        return Optional
                .ofNullable(filename).filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public void validateNumberOfImages(IncomingImagesDTO images,Errors errors){
        if(images.getImages().length > 10)
            errors.rejectValue("images", "images.error", "Limit is 10 images only");
    }

    @SneakyThrows
    private void isPicture(MultipartFile file, Errors errors) {
        //we check whether sent files are actually images
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");
        String filename = file.getOriginalFilename();
        if (filename != null) {
            Optional<String> extension = getExtension(filename);
            if (extension.isEmpty())
                errors.rejectValue("images", "images.error", "File missing extension");
            else if (!allowedExtensions.contains(extension.get().toLowerCase()))
                errors.rejectValue("images", "images.error", "File not in image format");
        }
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
    }
}