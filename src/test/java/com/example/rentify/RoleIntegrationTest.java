package com.example.rentify;

import com.example.rentify.entity.Role;
import com.example.rentify.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RentifyApplication.class)
public class RoleIntegrationTest {
    //injectujemo repozitori sloj da bi testirali upite

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleIntegrationTest.class);

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void insert() {
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setName("visitor");
        Role role2 = new Role();
        role2.setName("admin");
        Role role3 = new Role();
        role3.setName("registered user");
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roleRepository.saveAll(roles);
    }

    @Test
    public void finAllTest() {
        List<Role> roles = roleRepository.findAll();
        LOGGER.info("{}", roles);
    }

    @Test
    public void findByIdTest() {
        Optional<Role> roleOptional = roleRepository.findById(1);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            LOGGER.info("{}", role);
        } else
            LOGGER.error("no such role found");
    }

    @Test
    public void deleteByIdTest() {
//ili .delete(objekat) ali objekat mora imati primarni kljuc ddefinisan
        roleRepository.deleteById(1);
    }

    //testiranje nasih metoda

    @Test
    public void nameStartingWith() {
        List<Role> roles = roleRepository.findByNameStartingWith("ad");
        LOGGER.info("{}", roles);
    }
}
