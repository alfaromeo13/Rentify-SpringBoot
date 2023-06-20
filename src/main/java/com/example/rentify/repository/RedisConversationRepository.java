package com.example.rentify.repository;

import com.example.rentify.entity.RedisConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisConversationRepository extends CrudRepository<RedisConversation,String> {
    //findBy username 1 and username 2 order by message.timestamp desc ili asc
    //ovo napisi u jpql za vracanje poruka
}
