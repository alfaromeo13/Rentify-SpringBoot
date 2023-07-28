package com.example.rentify.service;

import com.example.rentify.dto.NotificationDTO;
import com.example.rentify.entity.Notification;
import com.example.rentify.entity.User;
import com.example.rentify.mapper.NotificationMapper;
import com.example.rentify.repository.NotificationRepository;
import com.example.rentify.repository.UserRepository;
import com.example.rentify.ws.TopicConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    public List<NotificationDTO> getNotificationsForUser(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Notification> notificationPage = notificationRepository.findNotifications(username, pageable);
        return notificationPage.hasContent() ?
                notificationMapper.toDTOList(notificationPage.getContent()) : Collections.emptyList();
    }

    public void delete(Integer id) {
        Notification notification= notificationRepository.getById(id);
        notificationRepository.delete(notification);
    }

    public void save(NotificationDTO notificationDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findByUsername(username);
        User receiver = userRepository.findByUsername(notificationDTO.getReceiverUsername());
        Notification notification = new Notification();
        notification.setMessage(notificationDTO.getMessage());
        notification.setSender(sender);
        notification.setReceiver(receiver);
        Notification savedNotification = notificationRepository.save(notification);
        messagingTemplate.convertAndSend(TopicConstants.NOTIFICATION_TOPIC, notificationMapper.toDTO(savedNotification));
    }
}