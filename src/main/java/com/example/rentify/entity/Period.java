package com.example.rentify.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "periods")
public class Period {
    @Id
    @Column
    private String name;
}