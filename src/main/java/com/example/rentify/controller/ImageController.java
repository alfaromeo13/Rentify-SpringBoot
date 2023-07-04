package com.example.rentify.controller;

import com.example.rentify.dto.ImagePreview;
import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.service.ImageService;
import com.example.rentify.validator.ImageUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;
    private final ImageUpdateValidator imgValidator;

    //vlasnik brise sliku za odredjeni apartman
    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/image/2
    @PreAuthorize("@imageAuth.hasPermission(#id)")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("add-to-apartment/{id}")//vlasnik dodaje sliku na apartman pri editovanju
    @PreAuthorize("@apartmentAuth.hasPermission(#id)")//POST http://localhost:8080/api/image/add-to-apartment/3
    public ResponseEntity<Void> multiPartHandler(@PathVariable Integer id, @RequestParam MultipartFile[] images) {
        IncomingImagesDTO imagesDTO = new IncomingImagesDTO(id, images);
        ValidationUtils.invokeValidator(imgValidator, imagesDTO, new BeanPropertyBindingResult(imagesDTO, "imagesDTO"));
        imageService.add(imagesDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("preview/{id}") //testing preview api
    public ResponseEntity<ImagePreview> preview(@PathVariable Integer id) throws IOException {
        ImagePreview imagePreview = imageService.getEncodedById(id);
        return ResponseEntity.ok(imagePreview);
    }
}