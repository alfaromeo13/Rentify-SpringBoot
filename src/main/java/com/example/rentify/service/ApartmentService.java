package com.example.rentify.service;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.entity.Apartment;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.specs.ApartmentSearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    public List<ApartmentDTO> getAll() {
        return null;
    }

    public ApartmentDTO findOneById(Integer id) {
        return null;
    }

    public List<ApartmentDTO> findByCategory(String category) {
        return null;
    }

    public List<Apartment> search(ApartmentSearchSpecification apartmentSearchSpecification) {
        return apartmentRepository.findAll(apartmentSearchSpecification);
        //da bi ovo pozvali ovako iznad tj da bi samo predali searchSpecifikaciju upitu
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
