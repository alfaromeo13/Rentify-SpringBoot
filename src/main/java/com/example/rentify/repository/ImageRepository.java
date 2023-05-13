package com.example.rentify.repository;

import com.example.rentify.entity.Image;
import com.example.rentify.projections.ImageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    boolean existsByIdAndApartmentUserUsername(Integer id, String username);

    @Query(value = "select sum(image.size) as size ,count(image.id) as number" +
            " from Image image join image.apartment apartment where apartment.id = :id")
    ImageProjection numAndSum(@Param("id") Integer id);
}