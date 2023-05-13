package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "images")
public class Image {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String path; //full path to the image

    @Column(name = "size_mb")
    private Double size;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "apartment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;
}