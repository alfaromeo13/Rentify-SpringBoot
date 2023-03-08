package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attributes")
public class Attribute {
    @Id
    @Column
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "attribute")
    private List<ApartmentAttribute> apartmentAttributes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
