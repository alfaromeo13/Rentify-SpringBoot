package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(name = "apartments")
public class Apartment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "square_meters")
    private Integer sqMeters;

    @Column
    private Double price;

    @Column(name = "number_of_bedrooms")
    private Integer numOfBedrooms;

    @Column(name = "number_of_bathrooms")
    private Integer numOfBathrooms;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "contact_number")
    private String number;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 1",
            insertable = false, updatable = false)
    private Boolean isActive;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "address_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<ApartmentAttribute> apartmentAttributes = new ArrayList<>();

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Rental> rentals = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Video> videos = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Image> images = new ArrayList<>();
}