package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.repository.ApartmentAttributeRepository;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.ImageRepository;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.specs.ApartmentIdSpecification;
import com.example.rentify.specs.ApartmentSearchSpecification;
import com.example.rentify.specs.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ImageRepository imageRepository;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentAttributeRepository apartmentAttributeRepository;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearch params) {
        Filter filter = new Filter(params);
        Page<Apartment> apartmentsPage = apartmentRepository.findAll(new ApartmentIdSpecification(filter), pageable);
        if (apartmentsPage.hasContent()) { //ids are apartment ids
            List<Integer> ids = apartmentsPage.getContent().stream().map(Apartment::getId).collect(Collectors.toList());
            List<Apartment> apartments = apartmentRepository.findAll(new ApartmentSearchSpecification(filter, ids));
            return apartmentMapper.toDTOList(apartments);
        } else return Collections.emptyList();
    }

    public void save(ApartmentDTO apartmentDTO) {
        //when saved apartment has an 'id' given by db. table and we need it
        Apartment apartment = apartmentRepository.save(
                apartmentMapper.toEntity(apartmentDTO));
        apartment.getApartmentAttributes().forEach(at -> at.setApartment(apartment));
        apartmentAttributeRepository.saveAll(apartment.getApartmentAttributes());
        apartment.getImages().forEach(im -> im.setApartment(apartment));
        imageRepository.saveAll(apartment.getImages());
    }

    public Boolean update(Integer id, ApartmentDTO apartmentDTO) {
        boolean apartmentExists = apartmentRepository.existsById(id);
        if (apartmentExists) {
            apartmentDTO.setId(id);
            save(apartmentDTO);
            return true;
        } else return false;
    }

    public Boolean delete(Integer id) {
        Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
        if (apartmentOptional.isPresent()) {
            Apartment apartment = apartmentOptional.get();
            apartment.setIsActive(false);
            apartmentRepository.save(apartment);
            return true;
        } else return false;
    }
}