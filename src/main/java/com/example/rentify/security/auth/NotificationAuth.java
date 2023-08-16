package com.example.rentify.security.auth;

import com.example.rentify.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAuth { // notificationAuth <- name of the Bean

    private final NotificationRepository notificationRepository;

    public boolean hasPermission(Integer notificationId) { //checking if we received notification
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return notificationRepository.existsByIdAndReceiverUsername(notificationId, username);
    }
}