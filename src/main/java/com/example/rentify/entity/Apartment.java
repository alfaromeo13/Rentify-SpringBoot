package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @JsonBackReference
    @JoinColumn(name = "address_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<ApartmentAttribute> apartmentAttributes = new ArrayList<>();

    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Review> reviews = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Rental> rentals = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Video> videos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "apartment")
    private List<Image> images = new ArrayList<>();

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ApartmentAttribute> getApartmentAttributes() {
        return apartmentAttributes;
    }

    public void setApartmentAttributes(List<ApartmentAttribute> apartmentAttributes) {
        this.apartmentAttributes = apartmentAttributes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
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

    public void setUser(User user) {
        this.user = user;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}