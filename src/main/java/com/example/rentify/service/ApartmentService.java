package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.mapper.ImageMapper;
import com.example.rentify.projections.AdminApartmentProjection;
import com.example.rentify.repository.*;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.specs.ApartmentIdSpecification;
import com.example.rentify.specs.ApartmentSearchSpecification;
import com.example.rentify.specs.Filter;
import com.example.rentify.ws.TopicConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private final NotificationService notificationService;
    private final ApartmentMapper apartmentMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentIdSpecification idSpecification;
    private final ApartmentSearchSpecification apartmentSearchSpecification;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearch params) {
        filter.setApartmentSearch(params);
        Page<Apartment> apartmentsPage = apartmentRepository.findAll(idSpecification, pageable);
        if (apartmentsPage.hasContent()) {
            List<Integer> ids = apartmentsPage.getContent().stream().map(Apartment::getId).collect(Collectors.toList());
            apartmentSearchSpecification.setIds(ids);//these are apartment ids
            List<Apartment> apartments = apartmentRepository.findAll(apartmentSearchSpecification, pageable.getSort());
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
        Apartment savedApartment = apartmentRepository.save(apartment);
        ApartmentDTO savedDTO = apartmentMapper.toDTO(savedApartment);
        savedDTO.setCreatedAt(new Date());
        messagingTemplate.convertAndSend(TopicConstants.CREATED_APARTMENT_TOPIC, savedDTO);
    }

    public void activateById(Integer id) {
        if (apartmentRepository.existsById(id)) {
            Apartment apartment = apartmentRepository.getById(id);
            apartment.setIsActive(true);
            apartmentRepository.save(apartment);
        }
    }

    public void approveById(Integer id) {
        if (apartmentRepository.existsById(id)) {
            Apartment apartment = apartmentRepository.getById(id);
            apartment.setIsApproved(true);
            apartmentRepository.save(apartment);
            NotificationDTO notification=new NotificationDTO();
            notification.setReceiverUsername(apartment.getUser().getUsername());
            notification.setMessage("Your renting property is approved and is now visible for all! :) ");
            notificationService.save(notification);
       }
    }

    public void update(Integer id, ApartmentDTO apartmentDTO) {
        apartmentDTO.setId(id);
        apartmentDTO.setIsActive(true);
        apartmentDTO.setImages(imageMapper.toDTOList(apartmentRepository.getById(id).getImages()));
        save(apartmentDTO);
    }

    public AdminApartmentProjection getProjection(){
        return apartmentRepository.getProjection();
    }

    public boolean existsById(Integer id) {
        return apartmentRepository.existsById(id);
    }

    public void delete(Integer id) {
        Apartment apartment = apartmentRepository.getById(id);
        apartment.setIsActive(false);
        apartmentRepository.save(apartment);
    }

    public void notApprove(Integer id) {
        Apartment apartment = apartmentRepository.getById(id);
        NotificationDTO notification=new NotificationDTO();
        notification.setReceiverUsername(apartment.getUser().getUsername());
        notification.setMessage("Sorry...Your property for renting has been declined :/ ");
        notificationService.save(notification);
        apartmentRepository.delete(apartment);
    }
}