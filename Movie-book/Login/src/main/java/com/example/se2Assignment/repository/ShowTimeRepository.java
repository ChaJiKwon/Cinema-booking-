package com.example.se2Assignment.repository;

import com.example.se2Assignment.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    public Long countById(Long id);
}
