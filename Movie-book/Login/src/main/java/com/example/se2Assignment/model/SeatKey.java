package com.example.se2Assignment.model;





import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "seat_key")
public class SeatKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long showtimeId;
    private LocalDate localDate;

    public SeatKey(){

    }
    public SeatKey(Long showtimeId, LocalDate localDate) {
        this.showtimeId = showtimeId;
        this.localDate = localDate;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatKey seatKey = (SeatKey) o;
        return Objects.equals(showtimeId, seatKey.showtimeId) &&
                Objects.equals(localDate, seatKey.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showtimeId, localDate);
    }
}
