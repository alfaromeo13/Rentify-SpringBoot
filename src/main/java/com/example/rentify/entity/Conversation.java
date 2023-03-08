package com.example.rentify.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @JoinColumn(name = "user_1_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @JsonBackReference
    @JoinColumn(name = "user_2_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @JsonManagedReference
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
