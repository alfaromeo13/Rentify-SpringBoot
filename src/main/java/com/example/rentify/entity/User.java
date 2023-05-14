package com.example.rentify.entity;

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

    @Column(name = "created_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 0",
            insertable = false)
    private Boolean isActive;

    // we set in phpMyAdmin current_timestamp(0) and isActive=0 as default values
    // also we said that the field can be null,meaning if someone inserts this object in database
    // without these 2 fields,the default values will be set for them

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id", referencedColumnName = "id"))
    private List<Apartment> favoriteApartments = new ArrayList<>();//unidirectional relationship

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user1")
    private List<Conversation> sender = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user2") //second user is the one we talk to
    private List<Conversation> receiver = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "receiver")
    private List<Message> messagesFromReceiver = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "sender")
    private List<Message> messagesFromSender = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Apartment> apartments = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Rental> rentals = new ArrayList<>();

    public void addRole(Role role) { //adds role if it isn't already present
        if (roles.stream().noneMatch(r -> r.getName().equals(role.getName())))
            roles.add(role);
    }

    public void removeRoleById(int id) { //remove role if it exists
        roles.stream()
                .filter(role -> role.getId() == id)
                .findFirst().ifPresent(role -> roles.remove(role));
    }

    public void addFavouriteApartment(Apartment apartment) {
        if (favoriteApartments.stream().noneMatch(a -> a.getId().equals(apartment.getId())))
            favoriteApartments.add(apartment);
    }

    public void removeFavouriteApartmentById(int id) {
        favoriteApartments.stream()
                .filter(apartment -> apartment.getId() == id)
                .findFirst().ifPresent(apartment -> favoriteApartments.remove(apartment));
    }
}