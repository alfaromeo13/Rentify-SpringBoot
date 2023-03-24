package com.example.rentify.repository;

import com.example.rentify.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "select message from Message message " +
            "join message.conversation conversation where conversation.id = :id")
    Page<Message> findMessagesForConversationId(@Param("id") Integer id, Pageable pageable);
}
