package com.example.rentify.controller;

import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.entity.Notification;
import com.example.rentify.exception.ValidationException;
import com.example.rentify.service.NotificationService;
import com.example.rentify.validator.NotificationValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationValidator notificationValidator;

    //get notifications for specific user with pageable
    @GetMapping //GET http://localhost:8080/api/notification?page=0&size=5
    public ResponseEntity<List<NotificationDTO>> getNotificationsForUser(Pageable pageable) {
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(pageable);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping //create notification
    @SneakyThrows //POST http://localhost:8080/api/notification/
    public ResponseEntity<Void> create(@RequestBody NotificationDTO notificationDTO) {
        Errors errors = new BeanPropertyBindingResult(notificationDTO, "notificationDTO");
        ValidationUtils.invokeValidator(notificationValidator, notificationDTO, errors);
        if (errors.hasErrors()) throw new ValidationException("Validation error", errors);
        log.info("Adding notification : {} ", notificationDTO);
        notificationService.save(notificationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //delete notification
    @DeleteMapping("{id}") //DELETE http://localhost:8080/api/notification/55
    @PreAuthorize("@notificationAuth.hasPermission(#id)")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        notificationService.delete(id);
        log.info("Deleted notification with id : {} ", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}