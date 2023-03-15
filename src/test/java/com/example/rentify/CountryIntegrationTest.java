package com.example.rentify;

import com.example.rentify.entity.Country;
import com.example.rentify.projections.CountryIdAndShortCodeProjection;
import com.example.rentify.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest(classes = RentifyApplication.class)
public class CountryIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressIntegrationTest.class);

    @Test
    public void insertCountryTest() {
        Country country = new Country();
        country.setName("Bosna");
        country.setShortCode("BOS");
        countryRepository.save(country);
    }

    @Test
    public void getCountryIdAndCodeTest() {
        List<CountryIdAndShortCodeProjection> countries = countryRepository.findIdAndCodeUsingCustomProjection();
        for (CountryIdAndShortCodeProjection country : countries)
            LOGGER.info("ID={} | SHORT_CODE= '{}'", country.getId(), country.getShortCode());
    }

    @Test
    public void findAllWithPageable() {
        //find all sa pageablom vec postoji predefinisan za nas u interfejsu JPA
        //page od 0 krece vazda ali svakako to nama salje bek
        //redna stranica  a svaka stranica ima onoliko elemenata na njoj koliki je size
        Pageable pageable = PageRequest.of(2, 2);//ovaj interfejs mora da ima size i page a sort je opcion
        Page<Country> countryPage = countryRepository.findAllJPQL(pageable); //page =0 size=2
        LOGGER.info("Country page: {}", countryPage);

        //nekad cemo imati slucaj da treba da rapakujemo ove podatke iz page vrapera
        if (countryPage.hasContent()) { //da li u vraperu postoji neka kolekcija podatak tj sadrzaj
            //iz ovog vrapera mozemo izvuci informacije koje nam mogu biti korisne u procesuiranju
            List<Country> countries = countryPage.getContent();
            long totalElements = countryPage.getTotalElements();
            int totalPages = countryPage.getTotalPages();
        }
    }

    @Test
    public void findAll(){
        List<Country> countries=countryRepository.findAllCountries();
        LOGGER.info("");
    }
}
