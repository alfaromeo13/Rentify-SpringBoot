package com.example.rentify.controller;

import com.example.rentify.dto.ApartmentDTO;
import com.example.rentify.dto.RoleDTO;
import com.example.rentify.dto.UserDTO;
import com.example.rentify.dto.UserWithRolesDTO;
import com.example.rentify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /*{"firstName":"Jovan","lastName": "Vukovic","email":"bula20@gmail.com",
       "username":"bula12345!","password":"`1212344312134"} */
    @PostMapping //POST http://localhost:8080/api/user
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        log.info("Storing user : {} ", userDTO);
        userService.save(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //{"id":3, "name":"admin"}
    @PutMapping("{id}/add-role") //PUT http://localhost:8080/api/user/10/add-role
    public ResponseEntity<Void> addUserRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        boolean updated = userService.addRole(id, roleDTO);
        log.info("Adding a role to user with id: {}", id);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //{"id":3, "name":"admin"}
    @PutMapping("{id}/delete-role") //PUT http://localhost:8080/api/user/10/delete-role
    public ResponseEntity<Void> deleteUserRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        boolean deleted = userService.deleteRole(id, roleDTO);
        log.info("Deleting a role to user with id: {}", id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("with-roles/{id}") //GET http://localhost:8080/api/user/with-roles/9
    public ResponseEntity<UserWithRolesDTO> getByIdWithRoles(@PathVariable Integer id) {
        UserWithRolesDTO user = userService.findWithRoles(id);
        log.info("User : {} ", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/user/9
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deleting user with id: {} ", id);
        boolean deleted = userService.delete(id);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //POST http://localhost:8080/api/user/for-user/9/add-fav-apartment/3
    @PostMapping("for-user/{user-id}/add-fav-apartment/{apartment-id}")
    public ResponseEntity<Void> addFavourite(@PathVariable("user-id") Integer userId,
                                             @PathVariable("apartment-id") Integer apartmentId) {
        boolean updated = userService.addFavourites(userId, apartmentId);
        log.info("Adding a favourite apartment with id: {} to user with id: {}", apartmentId, userId);
        return new ResponseEntity<>(updated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //DELETE http://localhost:8080/api/user/for-user/9/delete-fav-apartment/3
    @DeleteMapping("for-user/{user-id}/delete-fav-apartment/{apartment-id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable("user-id") Integer userId,
                                                @PathVariable("apartment-id") Integer apartmentId) {
        log.info("For user with id:{} deleting favourite apartment with id: {} ", userId, apartmentId);
        boolean deleted = userService.deleteFavApartment(userId, apartmentId);
        return new ResponseEntity<>(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    //GET http://localhost:8080/api/user/fav-apartments-for-user?page=0&size=5&id=9
    @GetMapping("fav-apartments-for-user")
    public ResponseEntity<List<ApartmentDTO>> getByIdWithRoles(Pageable pageable, @RequestParam("id") Integer id) {
        List<ApartmentDTO> apartments = userService.findFavouriteApartmentsForUserId(id, pageable);
        log.info("Favourite apartments : {} ", apartments);
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    //update user

    //get by user id

    //get all users with paging(this is important for admin)

    //get rentals(kome si sto izdao i sl)

    //add rental

    //update rental

    //delete rental
}