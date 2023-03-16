package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.mapper.ApartmentMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.specs.ApartmentSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    public List<ApartmentDTO> search(Pageable pageable, ApartmentSearchSpecification apartmentSearchSpecification) {
        Page<Apartment> apartmentsPage = apartmentRepository.findAll(apartmentSearchSpecification, pageable);
        if (apartmentsPage.hasContent()) {
            return apartmentMapper.toDTOList(apartmentsPage.getContent());
        } else return new ArrayList<>();
        //da bi samo predali searchSpecifikaciju upitu
        //moramo nasliejditi u repozitori sloju JpaSpecificationExecutor i ako ovo sad nasliejdimo
        //moci cemo da pozovemo neke nove metode koje su ugradjene pa sada mozemo pzovati
        //.findAll(apartmentSearchSpecification); ranije nismo mogli
        //sada posto smo proslijedili search specifikaciju hibernater ce pozvati toPredicate metrodu
        //koja vrace glavni where uslov! :D
        //kada predamo odredjenu specifikaciju mozda i ne bude where uslova ako niko nije specificirao sta zeli
        //dakle pozivom findAll hibernate poziva toPredicate da bi dobili dinamicki where uslov i dalje
        //ga nihbernate uvrstava u sami upit
    }
}