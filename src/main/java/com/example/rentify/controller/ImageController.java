package com.example.rentify.controller;

import com.example.rentify.dto.ImagePreview;
import com.example.rentify.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("preview/{id}") //testing preview api
    public ResponseEntity<ImagePreview> preview(@PathVariable Integer id) throws IOException {
        ImagePreview imagePreview = imageService.getEncodedById(id);
        return ResponseEntity.ok(imagePreview);
    }
}