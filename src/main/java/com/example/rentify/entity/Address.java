package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //umjesto ovoga troje treba cuvati x,y i String naziv adrese
    @Column
    private String street;

    @Column
    private String number;

    @Column(name = "postal_code")
    private String code;
    //
    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "neighborhood_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Neighborhood neighborhood;
}