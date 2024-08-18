package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Seat;
import com.example.se2Assignment.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    @Autowired
    SeatRepository seatRepo;
    public List<Seat> listAllSeats(){
        return seatRepo.findAll();
    }

    public void saveSeats(Seat seat){
        seatRepo.save(seat);
    }
    public Seat get(Long id) throws RuntimeException{
        Optional<Seat> result = seatRepo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new RuntimeException("Could not found seat with id: " + id);
    }
    @Transactional
    public void delete(Long id){
        seatRepo.deleteById(id);
    }
    public List<Seat> findAllById(List<Long> ids) {
        return seatRepo.findAllById(ids);
    }

}
