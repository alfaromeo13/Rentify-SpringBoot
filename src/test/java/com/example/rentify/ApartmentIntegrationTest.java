package com.example.rentify;

import com.example.rentify.repository.ApartmentRepository;
import lombok.experimental.StandardException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.criteria.CriteriaBuilder;

@SpringBootTest(classes = RentifyApplication.class)
public class ApartmentIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentIntegrationTest.class);
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    public void finaAll() {

    }


}
