package com.example.se2Assignment.service;

import com.example.se2Assignment.model.SeatKey;
import com.example.se2Assignment.repository.SeatKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatKeyService {
    @Autowired
    SeatKeyRepository seatKeyRepository;

    public void save(SeatKey seatKey){
        seatKeyRepository.save(seatKey);
    }
}
