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
@Table(name = "addresses")
public class Address {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double x;

    @Column
    private Double y;

    @Column
    private String street;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "neighborhood_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Neighborhood neighborhood;
}