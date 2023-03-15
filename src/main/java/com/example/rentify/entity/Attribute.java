package com.example.rentify.entity;

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
