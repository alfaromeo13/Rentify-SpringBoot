package com.example.rentify.service;

import com.example.rentify.dto.ImageDTO;
import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.entity.Image;
import com.example.rentify.mapper.ImageMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final ApartmentRepository apartmentRepository;

    public void add(IncomingImagesDTO imagesDTO) {
        Set<ImageDTO> savedImages = saveToFs(imagesDTO.getImages());
        for (ImageDTO imageDTO : savedImages) {
            Image image = imageMapper.toEntity(imageDTO);
            image.setApartment(apartmentRepository.getById(imagesDTO.getApartmentId()));
            imageRepository.save(image);
        }
    }

    @SneakyThrows
    public Set<ImageDTO> saveToFs(MultipartFile[] images) {
        //we store image path in db while actual image is stored on fs
        String basePath = "/home/ja/Downloads/Images/";
        Set<ImageDTO> imageDTOs = new HashSet<>();
        for (MultipartFile image : images) {
            /* To prevent file overwriting, we assign unique identifiers to file names using randomUUID(),
             which ensures uniqueness even when called concurrently by multiple threads (using synchronization)*/
            String fullPath = basePath + UUID.randomUUID().toString() + image.getOriginalFilename();
            Path path = Paths.get(fullPath);
            Files.write(path, image.getBytes());
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setPath(fullPath);
            imageDTO.setSize(calculateSizeInMB(image));
            imageDTOs.add(imageDTO);
        }
        return imageDTOs;
    }

    public void delete(Integer id) {
        imageRepository.deleteById(id);
    }

    public double calculateSizeInMB(MultipartFile image) {
        return (double) image.getSize() / (1_000_000);
    }
}