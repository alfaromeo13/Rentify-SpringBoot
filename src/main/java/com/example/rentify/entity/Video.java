package com.example.rentify.entity;

import javax.persistence.*;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //path

    //...geteri seteri
}
