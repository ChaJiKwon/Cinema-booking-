package com.example.se2Assignment.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
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
    private int a_section;
    private int b_section;
    private int vip_section;
    private double plusCost;
    @ManyToMany(mappedBy = "theaters")
    private Set<Movie> movies = new HashSet<>();
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShowTime> showTimes = new HashSet<>();

    // Default constructor
    public Theater() {
    }



    // Parameterized constructor
    public Theater(String theaterName, String address, String imageUrl, String description, int a_section, int b_section, int vip_section, double plusCost) {
        this.theaterName = theaterName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.description = description;
        this.a_section = a_section;
        this.b_section = b_section;
        this.vip_section = vip_section;
        this.plusCost = plusCost;
    }

    // Getters and Setters
    public Long getId() {
        return id;
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

    public int getA_section() {
        return a_section;
    }

    public void setA_section(int a_section) {
        this.a_section = a_section;
    }

    public int getB_section() {
        return b_section;
    }

    public void setB_section(int b_section) {
        this.b_section = b_section;
    }

    public int getVip_section() {
        return vip_section;
    }

    public void setVip_section(int vip_section) {
        this.vip_section = vip_section;
    }
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<ShowTime> getShowTimes() {
        return showTimes;
    }


    public void setShowTimes(Set<ShowTime> showTimes) {
        this.showTimes = showTimes;
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
