package com.example.rentify.service;

import com.example.rentify.dto.*;
import com.example.rentify.entity.*;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public boolean activateAccount(String mail) {
        if (userRepository.existsByEmail(mail)) {
            User user = userRepository.findByEmail(mail);
            user.setIsActive(true);
            userRepository.save(user);
            return true;
        } else return false;
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

    public boolean isActive(String username) {
        return userRepository.existsByUsernameAndIsActiveTrue(username);
    }

    public void register(UserCreateDTO userCreateDTO) {
        //we inject PasswordEncoder bean from SecurityConfig and call encode() to make password in BCrypt format
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        User user = userMapper.createUserToEntity(userCreateDTO);
        Role role = roleRepository.findRoleByName("ROLE_REGISTERED");
        user.addRole(role);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        sendEmail(user);
    }

    //we schedule a task to run every hour
    @Scheduled(cron = "0 0 * * * *") // cron expressions
    public void checkSpam() {
        List<User> users = userRepository.findByIsActiveFalse();
        for (User user : users) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getCreatedAt());
            // add 15 minutes to the calendar
            calendar.add(Calendar.MINUTE, 15);
            //if 15min passed we delete that account
            if (calendar.getTime().before(new Date()))
                userRepository.delete(user);
        }
    }

    @SneakyThrows
    private void sendEmail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("jovanvukovic09@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your registration");
        //url is api for confirming user account(anyone can access this api because of security config)
        String url = "http://localhost:8080/api/authenticate/verify?mail=" + user.getEmail();
        helper.setText("<img src='cid:identifier1234'><br>"
                + "Dear " + user.getFirstName() + ",<br>"
                + "To activate your account, please click on the link below<br>"
                + "<h3><a href='" + url + "' target=\"_self\"> Activate now </a></h3><br>"
                + "Thank you,<br> Rentify", true);
        FileSystemResource res = new FileSystemResource(new File("img/rentify.png"));
        helper.addInline("identifier1234", res);
        mailSender.send(message);
    }
}