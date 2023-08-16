package com.example.rentify.repository;

import com.example.rentify.entity.ResetPasswordRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisResetPasswordRepository extends CrudRepository<ResetPasswordRedis, String>{
}
