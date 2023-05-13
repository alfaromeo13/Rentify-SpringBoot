package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "rentals")
public class Rental {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    @Temporal(value = TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(value = TemporalType.DATE)
    private Date endDate;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @ToString.Exclude
    @JoinColumn(name = "status")
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status = new Status();

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "apartment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;
}