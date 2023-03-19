package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.specs.ApartmentSearchSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearchSpecification apartmentSearchSpecification) {
        Page<Apartment> apartmentsPage = apartmentRepository.findAll(apartmentSearchSpecification, pageable);
        log.info("Cache miss..Getting data from database.");
        if (apartmentsPage.hasContent()) {
            return apartmentMapper.toDTOList(apartmentsPage.getContent());
        } else return new ArrayList<>();
        //hibernate calls toPredicate which generates where... and passes it to query
    }

    public void save(ApartmentDTO apartmentDTO) {
        apartmentRepository.save(apartmentMapper.toEntity(apartmentDTO));
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