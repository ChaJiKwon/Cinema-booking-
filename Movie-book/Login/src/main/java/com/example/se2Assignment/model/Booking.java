package com.example.se2Assignment.model;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numberOfTickets;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // Default constructor
    public Booking() {}

    public Booking(int numberOfTickets,  User user){
        this.numberOfTickets = numberOfTickets;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
