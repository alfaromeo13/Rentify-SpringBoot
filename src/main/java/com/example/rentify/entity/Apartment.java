package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "created_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "contact_number")
    private String number;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 1",
            insertable = false)
    private Boolean isActive;

    @Column(name = "is_approved",
            columnDefinition = "TINYINT(1) DEFAULT 0",
            insertable = false)
    private Boolean isApproved;

    @ToString.Exclude
    @JoinColumn(name = "address_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @JoinColumn(name = "period_name")
    @ManyToOne(fetch = FetchType.LAZY)
    private Period period;

    @JoinColumn(name = "property_type")
    @ManyToOne(fetch = FetchType.LAZY)
    private PropertyType propertyType;

    @Column(
            columnDefinition = "INT(11) DEFAULT 1",
            insertable = false)
    private Integer grade;

    @ToString.Exclude
    @JsonManagedReference
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private Set<ApartmentAttribute> apartmentAttributes = new HashSet<>();

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
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    //on setter level we define attribute reference for 'this' apartment
    public void setApartmentAttributes(Set<ApartmentAttribute> apartmentAttributes) {
        this.apartmentAttributes = apartmentAttributes;
        this.apartmentAttributes.forEach(apartmentAttribute -> apartmentAttribute.setApartment(this));
    }

    //same goes for images
    public void setImages(List<Image> images) {
        this.images = images;
        this.images.forEach(image -> image.setApartment(this));
    }
}