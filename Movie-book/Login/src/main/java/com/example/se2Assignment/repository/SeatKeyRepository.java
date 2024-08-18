package com.example.se2Assignment.repository;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.SeatKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatKeyRepository  extends JpaRepository<SeatKey,Long> {

}
