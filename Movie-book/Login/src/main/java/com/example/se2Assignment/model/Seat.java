package com.example.se2Assignment.model;

import com.example.se2Assignment.model.SeatKey;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seat_id;
    private int number;
    private String type;
    private boolean isVacant =true;
    @ElementCollection
    private Map<SeatKey, Boolean> seatStatus;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    public Seat() {

    }
    public Seat(int number, String type) {
        this.number = number;
        this.type = type;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean vacant) {
        isVacant = vacant;
    }

    public Long getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Long seat_id) {
        this.seat_id = seat_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Map<SeatKey, Boolean> getSeatStatus() {
        return seatStatus;
    }
    public void setStatus(Long showTimeId, LocalDate date, Boolean status) {
        seatStatus.put(new SeatKey(showTimeId, date), status);
    }
    public void setSeatStatus(Map<SeatKey, Boolean> seatStatus) {
        this.seatStatus = seatStatus;
    }
}

