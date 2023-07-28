package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "notifications")
public class Notification {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String message;

    @Column(name = "created_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "from_user")
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "to_user")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;
}