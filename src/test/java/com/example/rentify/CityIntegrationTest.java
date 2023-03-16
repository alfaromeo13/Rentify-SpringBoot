package com.example.rentify;

import com.example.rentify.entity.City;
import com.example.rentify.entity.Country;
import com.example.rentify.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RentifyApplication.class)
public class CityIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityIntegrationTest.class);

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void getAllCitiesFromGivenCountryTest() {
//        List<City> cities = cityRepository.getAllCitiesFromCountryJPQL("MNE");
//        LOGGER.info("{}", cities);
    }

    //metod koji izvlaci gread po odredjenom id-iu
    @Test
    public void getCityByIdTest() {
        Optional<City> cityOptional = cityRepository.findById(2);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();//ovaj city nece imati podatak o drzavi
            //jer je relacija ka drzavi LAZY a u pitanju nije upit koji smo mi napravili
            //pa nema join fetch.Ako nam u ovom momentu trebaju podaci o drzavi
            //samo napisemo city.getCountry() kreirace se upit u pozadini koji ce vratiti country
            //u memoriji.Ili smo mogli napisati nasu JPQL metodu koja vrace country isto
            //ovo je dobra stvar jer necemio opterecivati memoriju jer na zahtjve dovlacimo podatke
            //bas u onom momentu kada nam je to pottrebno.Ovo povlacenje na zahtjev moze se uraditi szmo u
            //transakcionom kontekstu pa nam treba @Transactional.Transakcije se lako implementirasju
            //u spring butu samo koristeci ovaj dekorator
            //najgore sto smozmeo sa strajne performansi da uradim sa ovim je
            //for petlaj za sve gradove u bazi getCountry() pravi se onoliko upita koliko je gradova
            //ovo rjesavamo tako sto cemo u gradu imati podatak o drzavi tj pisacemo svoj JPQL i pozvati
            //tu nasu metodu.Primjer ispod ( ranije je pisalo .findAll() )
        }
    }

    @Test
    public void getCityWithCountries() {
//        List<City> cities = cityRepository.findAllWithCountries();
//        for (City city : cities) {
//            Country country = city.getCountry();
//        }
//        //LOGGER.info("{}", cities);
    }

    @Test
    public void insertCityCascadeTest() {
        Country country = new Country();
        country.setId(1);
        City city = new City();
        city.setName("Konik city");
        city.setCountry(country);
        cityRepository.save(city);
        //prvo ce se izvrsiti insert into countries pa insert into cities tj 2 upita\
        //jer da bi se unio grad treba nam prvo drzava na koju se grad odnosi
        //zapisi ovo na papir
    }
}
