package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.mapper.ImageMapper;
import com.example.rentify.repository.*;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.specs.ApartmentIdSpecification;
import com.example.rentify.specs.ApartmentSearchSpecification;
import com.example.rentify.specs.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final Filter filter;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final ReviewService reviewService;
    private final UserService userService;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentIdSpecification idSpecification;
    private final ApartmentSearchSpecification apartmentSearchSpecification;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearch params) {
        filter.setApartmentSearch(params);
        Sort sort = null;
        if (params.getSort() != null) {
            if (params.getSort().equals("price,asc")) {
                sort = Sort.by("price").ascending();
            } else if (params.getSort().equals("price,desc")) {
                sort = Sort.by("price").descending();
            } else if (params.getSort().equals("grade,asc")) {
                sort = Sort.by("grade").ascending();
            } else if (params.getSort().equals("grade,desc")) {
                sort = Sort.by("grade").descending();
            }
        }

        // Create the Pageable object with sorting, or without sorting if sort is null
        Pageable pageableWithSort = (sort != null) ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort)
                : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<Apartment> apartmentsPage = apartmentRepository.findAll(idSpecification, pageableWithSort);

        if (apartmentsPage.hasContent()) {
            List<Integer> ids = apartmentsPage.getContent().stream().map(Apartment::getId).collect(Collectors.toList());
            apartmentSearchSpecification.setIds(ids);//these are apartment ids
            List<Apartment> apartments = apartmentRepository.findAll(apartmentSearchSpecification);
            List<ApartmentDTO> newApartments=new ArrayList<>();
            apartments.forEach(apartment -> { //we calculate number of stars forEach apartment
                Double numberOfStars = reviewService.numberOfStars(apartment.getId());
                apartment.setGrade(numberOfStars != null ? (int) Math.round(numberOfStars) : 0);
                ApartmentDTO temp=apartmentMapper.toDTO(apartment);
                temp.setLiked(userService.isApartmentLiked(apartment.getId()));
                apartmentRepository.save(apartment);
                temp.setImages(imageService.encodeImages(apartment.getImages())); //we encode each apartment image to base64
                newApartments.add(temp);
            });
            return newApartments;
        } else return Collections.emptyList();
    }

    public void save(ApartmentDTO apartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment.setUser(userService.findByUsername(username));
        apartmentRepository.save(apartment);
    }

    public void update(Integer id, ApartmentDTO apartmentDTO) {
        apartmentDTO.setId(id);
        apartmentDTO.setIsActive(true);
        apartmentDTO.setImages(imageMapper.toDTOList(apartmentRepository.getById(id).getImages()));
        save(apartmentDTO);
    }

    public boolean existsById(Integer id) {
        return apartmentRepository.existsById(id);
    }

    public void delete(Integer id) {
        Apartment apartment = apartmentRepository.getById(id);
        apartment.setIsActive(false);
        apartmentRepository.save(apartment);
    }
}