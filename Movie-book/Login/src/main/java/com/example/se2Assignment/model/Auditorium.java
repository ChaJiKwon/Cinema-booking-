package com.example.se2Assignment.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "auditorium")
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditorium_id;
    private String name;
    private int totalSeats;
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(mappedBy = "auditorium")
    private List<Seat> seats;
    @OneToMany(mappedBy = "auditorium")
    private List<ShowTime> showTimes;

    public Auditorium(){}
    public Auditorium(String name,int totalSeats,Theater theater) {
        this.name = name;
        this.totalSeats=totalSeats;
        this.theater=theater;
    }

    public Long getAuditorium_id() {
        return auditorium_id;
    }

    public void setAuditorium_id(Long auditorium_id) {
        this.auditorium_id = auditorium_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }
    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}
