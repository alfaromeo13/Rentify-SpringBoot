package com.example.rentify.service;

import com.example.rentify.dto.CountryDTO;
import com.example.rentify.entity.Country;
import com.example.rentify.mapper.CountryMapper;
import com.example.rentify.projections.CountryIdAndShortCodeProjection;
import com.example.rentify.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<Country> findAll() {
        return countryRepository.findAllCountries();
    }

    public Page<Country> findAllWithPageable(Pageable pageable) {
        return countryRepository.findAllJPQL(pageable);
    }

    public List<Country> findAllPageable(Pageable pageable) {
        Page<Integer> countryIdsPage = countryRepository.findIdsPageable(pageable);
        List<Integer> countryIds = countryIdsPage.getContent();
        return countryRepository.findByIdIn(countryIds);
    }

    public List<CountryIdAndShortCodeProjection> findAllPa(Pageable pageable) {
        Page<CountryIdAndShortCodeProjection> countries
                = countryRepository.findAllPageable(pageable);
        return countries.getContent();
    }

    public void save(CountryDTO countryDTO) {
        //ne mozemo insertovati countryDTO jer je obicna klasa
        //vec jedino mozemo da insertujemo entitet.Pa ga pretvaramo u entitet
        Country country = countryMapper.toEntity(countryDTO);
        countryRepository.save(country);
    }

    public void update(Integer id, CountryDTO countryDTO) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            country.setName(countryDTO.getName());
            country.setShortCode(countryDTO.getShortCode());
            countryRepository.save(country);
        }
    }

    public void delete(Integer id) {
        countryRepository.deleteById(id);
    }
}