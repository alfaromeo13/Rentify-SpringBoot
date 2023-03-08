package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "is_active")
    private Boolean isActive;

    // we set in phpMyAdmin current_timestamp(0) and isActive=1 as default values
    // also we said that the field can be null,meaning if someone inserts this object in database
    // without these 2 fields,the default values will be set for them

    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id", referencedColumnName = "id"))
    private List<Apartment> favoriteApartments = new ArrayList<>();//unidirectional relationship

    @JsonManagedReference
    @OneToMany(mappedBy = "user1") //treba li mi?
    private List<Conversation> user1 = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user2") //second user is the one we talk to
    private List<Conversation> user2 = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "receiver")
    private List<Message> messagesFromReceiver = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "sender")
    private List<Message> messagesFromSender = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Apartment> apartments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Rental> rentals = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Apartment> getFavoriteApartments() {
        return favoriteApartments;
    }

    public void setFavoriteApartments(List<Apartment> favoriteApartments) {
        this.favoriteApartments = favoriteApartments;
    }

    public List<Conversation> getUser1() {
        return user1;
    }

    public void setUser1(List<Conversation> user1) {
        this.user1 = user1;
    }

    public List<Conversation> getUser2() {
        return user2;
    }

    public void setUser2(List<Conversation> user2) {
        this.user2 = user2;
    }

    public List<Message> getMessagesFromReceiver() {
        return messagesFromReceiver;
    }

    public void setMessagesFromReceiver(List<Message> messagesFromReceiver) {
        this.messagesFromReceiver = messagesFromReceiver;
    }

    public List<Message> getMessagesFromSender() {
        return messagesFromSender;
    }

    public void setMessagesFromSender(List<Message> messagesFromSender) {
        this.messagesFromSender = messagesFromSender;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}