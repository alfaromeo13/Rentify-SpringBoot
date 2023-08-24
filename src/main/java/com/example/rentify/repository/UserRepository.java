package com.example.rentify.repository;

import com.example.rentify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String mail);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndCode(String email,String code);

    boolean existsByUsernameAndIsActiveTrue(String username);

    @Query(value="select count(user.id) from User as user")
    Integer numOfUsers();

    @Query(value = "select user from User user join fetch user.roles " +
            " where user.username = :username")
    User findByUsername(@Param("username") String username);

    @Query(value= "select user from User user " +
            " where user.username like concat(:username , '%')")
    Page<User> findByUsernameLike(String username,Pageable pageable);

    @Query(value = "select user from User user join fetch user.roles where user.id = :id")
    User findUserWithRoles(@Param("id") Integer id);

    @Query(value="select user from User user join user.roles as roles where roles.name like 'ROLE_REGISTERED'")
    Page<User> findAllJPQL(Pageable pageable);

    @Query(value = "select apartment.id from User user " +
            "join user.favoriteApartments apartment where user.username = :username")
    Page<Integer> favouriteApartmentsForUser(@Param("username") String username, Pageable pageable);

    @Query(value = "select apartment.id from User user " +
            "join user.favoriteApartments apartment where apartment.id = :id and user.username = :username")
    Integer favouriteApartmentsForUser(@Param("username") String username, @Param("id") Integer id);
}