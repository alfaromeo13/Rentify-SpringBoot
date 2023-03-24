package com.example.rentify.repository;

import com.example.rentify.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    //user1 is sender and user2 is receiver

    @Query(value = "select conversation from Conversation conversation " +
            "join conversation.user1 user where user.id = :id")
    Page<Conversation> findAllConversationsByUserId(@Param("id") Integer id, Pageable pageable);

    @Query(value = "select conversation from Conversation conversation " +
            "join conversation.user2 user where user.username like concat(:username,'%')")
    Page<Conversation> findByUsername(@Param("username") String username, Pageable pageable);
}
