package com.example.rentify.controller;

import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //{"firstName":"Marko","lastName": "Bulatovic","email":"bula20@gmail.com",
    //"username":"bula12345!","password":"`1212344312134"}
    @PostMapping //POST http://localhost:8080/api/user
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.info("Storing user : {} ", userDTO);
        userService.save(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //{"id":3, "name":"admin"}
    @PutMapping("{id}/add-role") //PUT http://localhost:8080/api/user/10/add-role
    public ResponseEntity<Void> updateUserRole(@PathVariable Integer id,
                                               @RequestBody RoleDTO roleDTO) {
        boolean updated = userService.addRole(id, roleDTO);
        LOGGER.info("Adding a role to user with id={}", id);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/with-roles/{id}") //GET http://localhost:8080/api/user/with-roles/9
    public ResponseEntity<UserWithRolesDTO> getById(@PathVariable Integer id) {
        UserWithRolesDTO user = userService.findWithRoles(id);
        LOGGER.info("User : {} ", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

// LISTU OBJEKATA NPR onda bi to slali u jsonm ovako
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