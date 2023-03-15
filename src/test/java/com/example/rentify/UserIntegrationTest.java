package com.example.rentify;

import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RentifyApplication.class)
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserIntegrationTest.class);

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
        admin.setName("test123");

        Role developer = new Role();
        developer.setName("developer");

        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(developer);

        user.setRoles(roles);

        userRepository.save(user);
        //prvo ce dodati 2 nova usera
        //pa ce onda dodati 2 nove role
        //i tek onda ce to napraviti veze u pivot tabeli
    }

    @Test
    public void getUserWithRoles() {
        User u = userRepository.userWithRoles(5);
        LOGGER.info("JUST A TEST...");
    }

    @Test
    public void deleteRolesForUser() {
        //rolu brisemo super jednostavno korisniku preko hibernate-a
        Optional<User> userOptional = userRepository.findById(5);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRoles(null);
            userRepository.save(user); //UPDATE se radi
            //hibernate radi poredjenje tj select 2 selekt upita i uporedi te objekte da vidi
            //dje su ismjene i ako ima izmjena uradice delete
        }
    }

    @Test
    public void addRoleToExistingUserTest() {
        Optional<User> userOptional = userRepository.findById(5);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findById(2).orElse(null);
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Test
    public void addAnotherRoleToExistingUserTest() {
        User user = userRepository.userWithRoles(5);
        Role role = roleRepository.findById(3).orElse(null);
        user.addRole(role);//posto radimo geter nad LAZY moramo u @Transactional da smo
        //da smo satvili .setRoles i satvili novu rolu,stara se brise a nova se dodaje rola.
        //zato hibernate povlaci podatke iz baze u memoriji i poredi ih(zato radi one select upuite)
        //CESTA GRESKA JE DA SE STAVI SET ROLES i psoto je kaskadna operacija sve obrisu role od ranije
        //zato je po dobroj praksi u User klasi napraviti metodu addRole
        //u transakcionom kontekstu se SAVE automatski radi pa ga ne moramo pozivati
        //getBNyId je stvar koja radi samo u traksakciji
        userRepository.save(user);
    }

    //ipisivanje u konsoli omogucavamo sa onim iz konfiguracionog yaml fajla
    //ovo ce nam na kraju kada budemo pokretali app kao standalone java app prikazivati u
    //konzoli iz koje pokrecenmo jar fajl tu upite koji se izvrsavaju na bekendu


    @Test
    public void removeRoleFromExistingUserTest() {
        User user = userRepository.userWithRoles(5);
        user.removeRoleById(3);
        userRepository.save(user);//UPDATE jer user objekat ima predan id
    }
}