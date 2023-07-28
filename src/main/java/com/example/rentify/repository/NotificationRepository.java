package com.example.rentify.repository;

import com.example.rentify.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    boolean existsByIdAndReceiverUsername(Integer id,String username);

    @Query(value="select notification from Notification notification " +
            "join notification.receiver receiver where receiver.username = :username")
    Page<Notification> findNotifications(@Param("username") String username, Pageable pageable);
}