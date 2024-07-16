package com.example.se2Assignment.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTime showTime;
    private double price;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // Default constructor
    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;
    private int numTicket;
    private LocalDateTime endTime;
    public Ticket() {}

    public Ticket(int numTicket, ShowTime showTime, double price) {
        this.numTicket=numTicket;
        this.showTime = showTime;
        calculateEndTime();
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public static Duration parseDurationString(String durationString) {
        // Biểu thức chính quy để tìm các đoạn số trong chuỗi
        Pattern pattern = Pattern.compile("(\\d+)\\s*(hours?|minutes?)");
        Matcher matcher = pattern.matcher(durationString);

        long hours = 0;
        long minutes = 0;

        // Duyệt qua các phần của chuỗi và lấy giá trị hours và minutes
        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();

            if (unit.startsWith("hour")) {
                hours = value;
            } else if (unit.startsWith("minute")) {
                minutes = value;
            }
        }

        // Tính toán tổng thời gian
        return Duration.ofHours(hours).plusMinutes(minutes);
    }
    public void calculateEndTime() {
        Duration duration = parseDurationString(movie.getDuration());
        this.endTime = showTime.getShowDateTime().plus(duration);
    }

    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ShowTime getShowTime() {
        return showTime;
    }

    public void setShowTime(ShowTime showTime) {
        this.showTime = showTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
