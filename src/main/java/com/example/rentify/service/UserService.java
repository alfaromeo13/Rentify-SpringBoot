package com.example.rentify.service;

import com.example.rentify.dto.*;
import com.example.rentify.entity.Apartment;
import com.example.rentify.entity.Role;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JavaMailSender mailSender;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApartmentRepository apartmentRepository;

    public UserDTO find() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMapper.toDTO(userRepository.findByUsername(username));
    }

    public void update(UserDTO userDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }

    public void delete() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        user.setIsActive(false);
        userRepository.save(user);
    }

    public void addFavourites(Integer apartmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        Apartment apartment = apartmentRepository.getById(apartmentId);
        user.addFavouriteApartment(apartment);
        userRepository.save(user);
    }

    public void deleteFavourites(Integer apartmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        user.removeFavouriteApartmentById(apartmentId);
        userRepository.save(user);
    }

    public List<Integer> getFavourites(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Integer> apartmentsPage = userRepository.favouriteApartmentsForUser(username, pageable);
        return apartmentsPage.hasContent() ? apartmentsPage.getContent() : Collections.emptyList();
    }

    public List<UserDTO> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.hasContent() ? userMapper.toDTOList(usersPage.getContent()) : Collections.emptyList();
    }

    public void register(UserCreateDTO userCreateDTO) {
        //we inject PasswordEncoder bean from SecurityConfig and call encode() to make password in BCrypt format
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        User user = userMapper.createUserToEntity(userCreateDTO);
        Role role = roleRepository.findRoleByName("ROLE_REGISTERED");
        user.addRole(role);
        user.setPassword(encodedPassword);
        userRepository.save(user);
       // sendEmail(user);
        //napravi da vrati token koji ce trajati 15 minuta da potvrdi
    }

    private void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jovanvukovic09@gmail.com");
        message.setTo(user.getEmail());
        message.setText("Dear " + user.getFirstName() + "<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Rentify.");
        //umsjesto url cemo staviti link na api koji ce potvrditi korisnikovu registraciju
        message.setSubject("Please verify your registration");
        mailSender.send(message);
        log.info("Mail sent successfully!");

        /*


    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

    content = content.replace("[[URL]]", verifyURL);



    mailSender.send(message);
         */
    }
}