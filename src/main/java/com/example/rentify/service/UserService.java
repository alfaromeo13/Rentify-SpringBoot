package com.example.rentify.service;

import com.example.rentify.dto.*;
import com.example.rentify.entity.*;
import com.example.rentify.mapper.UserMapper;
import com.example.rentify.repository.ApartmentRepository;
import com.example.rentify.repository.RedisResetPasswordRepository;
import com.example.rentify.repository.RoleRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.security.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
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
    private final RedisResetPasswordRepository redisResetPasswordRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Integer numOfUsers(){
        return userRepository.numOfUsers();
    }

    public boolean activateAccount(String mail,String code) {
        if (userRepository.existsByEmailAndCode(mail,code)) {
            User user = userRepository.findByEmail(mail);
            user.setCode("");//we delete code
            user.setIsActive(true);
            userRepository.save(user);
            return true;
        } else return false;
    }

    public boolean changePassword(String mail, String code ,String newPassword) {
        ResetPasswordRedis resetCode = redisResetPasswordRepository.findById(mail).orElse(null);
        if (userRepository.existsByEmail(mail) && resetCode != null && resetCode.getGeneratedCode().equals(code)) {
            User user = userRepository.findByEmail(mail);
            user.setPassword(passwordEncoder.encode(newPassword));
            redisResetPasswordRepository.deleteById(mail);
            userRepository.save(user);
            return true;
        } else return false;
    }

    public boolean isApartmentLiked(Integer id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.equals("anonymousUser")) return false;
        Integer result =userRepository.favouriteApartmentsForUser(username, id);
        return result != null && result > 0;
    }

    public boolean delete(String password) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);;
        if(passwordEncoder.matches(password, user.getPassword())) {
            user.setIsActive(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void deleteById(Integer id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setIsActive(false);
            userRepository.save(user);
        }
    }

    public void activateById(Integer id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getById(id);
            user.setIsActive(true);
            userRepository.save(user);
        }
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

    public List<UserDTO> find(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.hasContent() ? userMapper.toDTOList(usersPage.getContent()) : Collections.emptyList();
    }

    public List<UserDTO> findAllByUsername(String username,Pageable pageable) {
        Page<User> usersPage = userRepository.findByUsernameLike(username,pageable);
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
        user.setCode(RandomString.make(8));
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    @SneakyThrows
    private void sendVerificationEmail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("jovanvukovic09@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Please verify your registration");
        String url = "http://localhost:4200/confirm?mail="+user.getEmail();
        helper.setText("<img src='cid:identifier1234'><br>"
                + "Dear " + user.getFirstName() + ",<br>"
                +" here is your secret code: <b><u>"+ user.getCode()+"</u></b><br>"
                + "To activate your account, please click on the link below<br>"
                + "<h3><a href='" + url + "' target=\"_self\"> Activate now </a></h3><br>"
                + "Thank you,<br> Rentify.", true);
        FileSystemResource res = new FileSystemResource(new File("img/logo.png"));
        helper.addInline("identifier1234", res);
        mailSender.send(message);
    }

    public boolean sendResetMail(String mail) {
        if (userRepository.existsByEmail(mail)) {
            sendResetPasswordMail(mail);
            return true;
        } else return false;
    }

    @SneakyThrows
    private void sendResetPasswordMail(String mail) {
        User user = userRepository.findByEmail(mail);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("jovanvukovic09@gmail.com");
        helper.setTo(mail);
        helper.setSubject("Code for password reset");
        String generatedCode =RandomString.make(8);
        ResetPasswordRedis code = new ResetPasswordRedis();
        code.setId(mail);
        code.setGeneratedCode(generatedCode);
        redisResetPasswordRepository.save(code);
        helper.setText("<img src='cid:identifier1234'><br>"
                + "Dear "+ user.getFirstName() +" ,<br>"
                +" here is your reset code: <b><u>"+ generatedCode +"</u></b><br>"
                +"<br> Rentify.", true);
        FileSystemResource res = new FileSystemResource(new File("img/logo.png"));
        helper.addInline("identifier1234", res);
        mailSender.send(message);
    }
}