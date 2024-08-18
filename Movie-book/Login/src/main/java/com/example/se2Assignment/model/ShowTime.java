package com.example.se2Assignment.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "showtime")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime showDateTime;
    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;
    @OneToMany(mappedBy = "showTime")
    private List<Ticket> ticket;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;


    public ShowTime() {}
    public ShowTime(LocalDateTime showDateTime, Theater theater) {
        this.showDateTime = showDateTime;
        this.theater = theater;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
