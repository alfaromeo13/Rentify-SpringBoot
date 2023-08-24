package com.example.rentify.service;

import com.example.rentify.dto.ImageDTO;
import com.example.rentify.dto.ImagePreview;
import com.example.rentify.dto.IncomingImagesDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Image;
import com.example.rentify.mapper.ImageMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final ApartmentRepository apartmentRepository;

    public void add(IncomingImagesDTO imagesDTO) {
            Apartment apartment = apartmentRepository.getById(imagesDTO.getApartmentId());
            Set<ImageDTO> savedImages = saveToFs(imagesDTO.getImages());
            for (ImageDTO imageDTO : savedImages) {
                Image image = imageMapper.toEntity(imageDTO);
                image.setApartment(apartment);
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
            imageDTOs.add(imageDTO);
        }
        return imageDTOs;
    }

    public void delete(Integer id) {
        imageRepository.deleteById(id);
    }

    public void deleteImagesWithIds(List<Integer> ids){
        ids.forEach(imageRepository::deleteById);
    }

    public Set<ImageDTO> encodeImages(List<Image> images) {
        Set<ImageDTO> encodeImages=new HashSet<>();
        images.forEach(image->{
            try {
                ImageDTO imageDTO=new ImageDTO();
                imageDTO.setId(image.getId());
                Path path = Paths.get(image.getPath());
                byte[] imageBytes = Files.readAllBytes(path);
                imageDTO.setPath(new String(Base64.getEncoder().encode(imageBytes)));
                encodeImages.add(imageDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return encodeImages;
    }

    public ImagePreview getEncodedById(Integer id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Path path = Paths.get(image.getPath());
        byte[] imageBytes = Files.readAllBytes(path);
        String encodedImage = new String(Base64.getEncoder().encode(imageBytes));
        return new ImagePreview(encodedImage, image.getPath().split("\\.")[1]);
    }
}