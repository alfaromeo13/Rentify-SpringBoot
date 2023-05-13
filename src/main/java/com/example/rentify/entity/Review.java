package com.example.rentify.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "reviews")
public class Review {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer grade;

    @Column
    private String comment;

    @Column(name = "created_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 1",
            insertable = false)
    private Boolean isActive;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "apartment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}