package com.example.rentify;

import com.example.rentify.entity.Role;
import com.example.rentify.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest(classes = RentifyApplication.class)
public class RoleIntegrationTest {

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
    public void findAllTest() {
        List<Role> roles = roleRepository.findAll();
        log.info("{}", roles);
    }

    @Test
    public void findAllJPQLTest() {
        List<String> roleNames = roleRepository.findAllNamesJPQL();
        log.info("{}", roleNames);
    }

    @Test
    public void findByIdTest() {
        Optional<Role> roleOptional = roleRepository.findById(1);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            log.info("{}", role);
        } else
            log.error("no such role found");
    }

    @Test
    public void deleteByIdTest() {
        roleRepository.deleteById(1);
    }
}