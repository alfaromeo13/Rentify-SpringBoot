package com.example.rentify;

import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest(classes = RentifyApplication.class)
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void insertUser() {
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("adminadmin");
        user.setFirstName("Jovan");
        user.setLastName("Vukovic");
        user.setEmail("jovanvukovic09@gmail.com");
        userRepository.save(user);
    }

    @Test
    public void insertUserRoleCascade() {
        User user = new User();
        user.setUsername("Marko");
        user.setPassword("adminadmin");
        user.setFirstName("Marko");
        user.setLastName("Vukovic");
        user.setEmail("markovukovic09@gmail.com");

        Role admin = new Role();
        admin.setName("admin");

        Role registered = new Role();
        registered.setName("registered user");

        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(registered);

        user.setRoles(roles);
        userRepository.save(user);
        //prvo ce dodati 2 nova usera
        //pa ce onda dodati 2 nove role
        //i tek onda ce to napraviti veze u pivot tabeli
    }

    @Test
    public void getUserWithRoles() {
        User user = userRepository.findUserWithRoles(8);
        log.info("{}", user);
    }

    @Test
    public void deleteRolesForUser() {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRoles(null);
            userRepository.save(user);
            //hibernate radi poredjenje tj 2 select upita
            // za uporedjenje objekata i ako ima izmjena uradice delete
        }
    }

    @Test
    public void addRoleToExistingUserTest() {
        Optional<User> userOptional = userRepository.findById(8);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findById(3).orElse(null);
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Test
    public void addAnotherRoleToExistingUserTest() {
        User user = userRepository.findUserWithRoles(8);
        Role role = roleRepository.findById(3).orElse(null);
        user.addRole(role);
        userRepository.save(user);
    }

    @Test
    public void removeRoleFromExistingUserTest() {
        User user = userRepository.findUserWithRoles(8);
        user.removeRoleById(3);
        userRepository.save(user);
    }
}