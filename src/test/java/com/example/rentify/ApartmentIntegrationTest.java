package com.example.rentify;

import com.example.rentify.dto.ApartmentAttributeDTO;
import com.example.rentify.dto.AttributeDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.ApartmentAttribute;
import com.example.rentify.entity.Attribute;
import com.example.rentify.mapper.ApartmentAttributeMapper;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.mapper.AttributeMapper;
import com.example.rentify.repository.ApartmentAttributeRepository;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.AttributeRepository;
import com.example.rentify.search.ApartmentSearch;
import com.example.rentify.specs.ApartmentSearchSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest(classes = RentifyApplication.class)
public class ApartmentIntegrationTest {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private AttributeMapper attributeMapper;

    @Test
    public void findAll() {
//        Pageable pageable = PageRequest.of(2, 2);
//        ApartmentSearch specs = new ApartmentSearch();
//        specs.setCityName("New York");
//        specs.setMinPrice(1100.0);
//        specs.setMaxPrice(4900.0);
//        Page<Apartment> apartmentPage =
//                apartmentRepository.findAll(
//                        new ApartmentSearchSpecification(specs), pageable);
//        if (apartmentPage.hasContent())
//            log.info("{}", apartmentPage.getContent());
    }

}