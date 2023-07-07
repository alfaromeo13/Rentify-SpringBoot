package com.example.rentify.ws.repository;

import com.example.rentify.entity.RedisConversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisConversationRepository extends CrudRepository<RedisConversation, String> {
}
