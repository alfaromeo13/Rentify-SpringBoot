package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
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
    //The insertable and updatable attributes are set to false
    // to tell Hibernate to use the default values when inserting or updating rows.
    //because Hibernate is inserting NULL values instead of using the default values
    // when you insert a new row.To use the default values in Hibernate, you can
    // specify the @Column annotation for each column in your Java entity class and
    // set the insertable and updatable attributes to false. This tells Hibernate to
    // not include the column in INSERT or UPDATE statements, which causes the database
    // to use the default value for the column.
    @Column(name = "created_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP",
            insertable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "is_active",
            columnDefinition = "TINYINT(1) DEFAULT 1",
            insertable = false)
    private Boolean isActive;

    // we set in phpMyAdmin current_timestamp(0) and isActive=1 as default values
    // also we said that the field can be null,meaning if someone inserts this object in database
    // without these 2 fields,the default values will be set for them

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany
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

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRoleById(int id) {
        roles.stream().filter(role -> role.getId() == id)
                .findFirst().ifPresent(role -> roles.remove(role));
    }
}