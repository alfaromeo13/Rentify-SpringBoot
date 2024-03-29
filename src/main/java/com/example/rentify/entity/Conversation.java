package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "conversations")
public class Conversation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 1",
            insertable = false)
    private Boolean isActive;

    @Column(name = "timestamp",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timestamp;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "user_1_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "user_2_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();
}