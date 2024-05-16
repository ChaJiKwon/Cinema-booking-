package com.example.se2Assignment.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Value("1")
    private long id;
    private String movieName;
    private String genre;
    private String cast;
    private String duration;
    private String rate;
    @Column(columnDefinition = "LONGTEXT")
    private String posterUrl;
    private String description;
    private double baseCost;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_theater",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "theater_id")
    )
    private Set<Theater> theaters = new HashSet<>();


    public Movie(String movieName, String genre, String cast, String duration, String rate, String posterUrl, String description, double baseCost) {
        this.movieName = movieName;
        this.genre = genre;
        this.cast = cast;
        this.duration = duration;
        this.rate = rate;
        this.posterUrl = posterUrl;
        this.description = description;
        this.baseCost = baseCost;
    }
    public Movie(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public Set<Theater> getTheaters() {
        return theaters;
    }

    public void setTheaters(Set<Theater> theaters) {
        this.theaters = theaters;
    }

}
