package com.example.rentify;

import com.example.rentify.entity.Address;
import com.example.rentify.entity.City;
import com.example.rentify.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = RentifyApplication.class)
public class AddressIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressIntegrationTest.class);

    @Test
    public void insertAddressTest() {
        Address address = new Address();
        address.setStreet("Mosorska");
        address.setNumber("bb");
        address.setCode("81000");
        City city = new City();
        city.setId(1);
        address.setCity(city);
        addressRepository.save(address);
    }

    @Test
    public void getAddressesByPostalCodeTest() {
        List<Address> addresses = addressRepository.getByCode("81000");
        LOGGER.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInSpecificCityTest() {
        List<Address> addresses = addressRepository.getAllInCity("Podgorica");
        LOGGER.info("{}", addresses);
    }

    @Test
    public void getAllAddressesInSpecificCountryTest() {//?
        List<Address> addresses = addressRepository.getAllInCountry("Montenegro");
        LOGGER.info("{}", addresses);
    }

    @Test
    public void getByIdWithCity() {
        List<Address> addresses = addressRepository.getByIdWithCity(1);
        LOGGER.info("{}", addresses);
    }
}