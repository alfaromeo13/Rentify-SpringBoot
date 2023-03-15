package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "attributes")
public class Attribute {
    @Id
    @Column
    private String name;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "attribute")
    private List<ApartmentAttribute> apartmentAttributes = new ArrayList<>();
}
