package com.example.rentify.entity;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //path

    //...geteri i seteri
}