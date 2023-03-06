package com.example.rentify.controller;

import com.example.rentify.dto.UserDTO;
import com.example.rentify.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    //handling all requests for user entity
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping //nekad postMapping treba da vrati insertovani obvjeat.Taj objekat sadrzi id novokreiranog
    //objekta u bazi.Sa strane klijenta miu ne bi trebali da proslijedimo id(ako bi se proslijedilo radio bi se PUtMapping kao)
    //i taj objekat koji se vrace ce imati sve unijetoo + id jer id unosi tabela kad se dodaje taj objekat
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {//mapiramo korisnika u odredjnu klasu
        LOGGER.info("User to be stored: {} ", user);//treba nam u user toString metoda
        return new ResponseEntity<>(new UserDTO(), HttpStatus.CREATED);//201 za insert tj ako je sv4e proslo ok 201 created
    }


    @PostMapping("with-categories")
    public ResponseEntity<Void> insertVoid(@RequestBody UserDTO user) {//mapiramo korisnika u odredjnu klasu
        LOGGER.info("User to be stored: {} ", user);//treba nam u user toString metoda
        return new ResponseEntity<>(HttpStatus.CREATED);//201 za insert tj ako je sv4e proslo ok 201 created
    }

//POST izaberi i body->raw->JSON i pisemo
//    {
//        "name" : "nesto",
//        "category": "nestoi",
//        "price":102.11
//    }
    //AKO NAM KLASA IMA LISTU OBJEKATA NPR onda bi to slali u jsonm ovako

//    {
//        "name" : "nesto",
//        "category": "nestoi",
//        "price":102.11,
//        "categories":[
//        {
//            "id": 1,
//            "name": "kat"
//        },
//        {
//            "id": 2,
//                "name": "kat"
//        }
//
//                ]
//    }

//kako bi ovo iznad premapirali u neku drugu klasu?
    //List<CategoryDTO> categories;//categoryDTo mora imati atribute kao sto su u ovoj listi da bi
    //spring mapirao te vrijesnoti
    //BTINO DTO klase mroaju imati getere i setere


    //ako ne posaljemo neko polje onda ce vr. toga polja biti null
    //spring but iskoristi setere iz klasa i u pozadini mapira JSON u nase klase


    //recimo da je JSON gore 'A' i da smo ovaj json gore ubacilk icijeli u
    // [
    // A,
    // A,
    // A,
    // ..
    // ]
    //ovo smo mogli mapirati sa listom  te same klase tj listom koja prima objekte te klase

    //contoler metoda(@RequestHeader(value="klju cheadera")String authentiocaition). Ovoc emo najvise korisatiti
    //kod autetifikacije klijenata.U 99.9% slucajeva u praksi imamo 1 request header i to ce biti autorizacioni token
    //u vecini slucajeva
}