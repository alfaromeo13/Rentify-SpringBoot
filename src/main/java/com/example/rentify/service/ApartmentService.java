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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final Filter filter;
    private final ImageMapper imageMapper;
    private final UserRepository userRepository;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentIdSpecification idSpecification;
    private final ApartmentSearchSpecification apartmentSearchSpecification;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearch params) {
        filter.setApartmentSearch(params);
        Page<Apartment> apartmentsPage = apartmentRepository.findAll(idSpecification, pageable);
        if (apartmentsPage.hasContent()) {
            List<Integer> ids = apartmentsPage.getContent().stream().map(Apartment::getId).collect(Collectors.toList());
            apartmentSearchSpecification.setIds(ids);//these are apartment ids
            List<Apartment> apartments = apartmentRepository.findAll(apartmentSearchSpecification);
            return apartmentMapper.toDTOList(apartments);
        } else return Collections.emptyList();
    }

    public void save(ApartmentDTO apartmentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment.setUser(userRepository.findByUsername(username));
        apartmentRepository.save(apartment);
    }

    public void update(Integer id, ApartmentDTO apartmentDTO) {
        apartmentDTO.setId(id);
        apartmentDTO.setIsActive(true);
        apartmentDTO.setImages(imageMapper.toDTOList(apartmentRepository.getById(id).getImages()));
        save(apartmentDTO);
    }

    public void delete(Integer id) {
        Apartment apartment = apartmentRepository.getById(id);
        apartment.setIsActive(false);
        apartmentRepository.save(apartment);
    }
}