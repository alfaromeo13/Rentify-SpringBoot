package com.example.rentify.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "apartments_attributes",
            joinColumns = @JoinColumn(name = "apartment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_name", referencedColumnName = "name"))
    private List<Attribute> attributes = new ArrayList<>();//sta cemo sa value????

    @OneToMany(mappedBy = "apartment")
    private List<Rental> rentals = new ArrayList<>();

    @OneToMany(mappedBy = "apartment")
    private List<Review> reviews = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSqMeters() {
        return sqMeters;
    }

    public void setSqMeters(Integer sqMeters) {
        this.sqMeters = sqMeters;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(Integer numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public Integer getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(Integer numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public User getUser() {
        return user;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void setUser(User user) {
        this.user = user;
    }
}