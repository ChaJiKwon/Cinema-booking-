package com.example.se2Assignment.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "theater")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Value("1")
    private Long id;
    private String theaterName;
    private String address;
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;
    private String description;
    private double plusCost;
    @ManyToMany(mappedBy = "theaters")
    private Set<Movie> movies = new HashSet<>();
    @OneToMany(mappedBy = "theater")
    private List<Auditorium> auditoriums;
    // Default constructor
    public Theater() {
    }

    // Parameterized constructor
    public Theater(String theaterName, String address, String imageUrl, String description,double plusCost
    ) {
        this.theaterName = theaterName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.description = description;
        this.plusCost = plusCost;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
    public double getPlusCost() {
        return plusCost;
    }

    public void setPlusCost(double plusCost) {
        this.plusCost = plusCost;
    }
    @Override
    public String toString() {
        return theaterName;
    }
}
