package com.example.se2Assignment.repository;

import com.example.se2Assignment.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    public Long countById(Long id);

}

