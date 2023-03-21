package com.example.rentify;

import com.example.rentify.entity.Address;
import com.example.rentify.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest(classes = RentifyApplication.class)
public class AddressIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void getAddressesByPostalCodeTest() {
        List<Address> addresses = addressRepository.findByCode("81000");
        log.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInSpecificNeighbourhoodTest() {
        List<Address> addresses = addressRepository.findAllInNeighborhood("Manhattan");
        log.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInSpecificCityTest() {
        List<Address> addresses = addressRepository.findAllInCity("Pod");
        log.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInCountryByNameTest() {
        List<Address> addresses = addressRepository.findAllInCountryByName("Mont");
        log.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInCountryByCodeTest() {
        List<Address> addresses = addressRepository.findAllInCountryByCode("ME");
        log.info("{}", addresses);
    }
}