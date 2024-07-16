package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.repository.AuditoriumRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriumService {
    @Autowired
    AuditoriumRepository audiRepo;

    public List<Auditorium> listAll(){
        return audiRepo.findAll();
    }
    public void save(Auditorium auditorium){
        audiRepo.save(auditorium);
    }


    public void delete(Long id){
        Auditorium auditorium = audiRepo.findById(id).orElseThrow(() -> new RuntimeException("Could not find auditorium with ID " + id));
        // Remove the associations between the movie and its theaters
        auditorium.getShowTimes().clear();
        auditorium.getSeats().clear();
        audiRepo.save(auditorium);
        audiRepo.deleteById(id);
    }
    public Auditorium getById(Long id){
        Optional<Auditorium> auditoriums = audiRepo.findById(id);
        if (auditoriums.isPresent()){
            return auditoriums.get();
        }
        throw new RuntimeException("Unable to find the auditorium with id: " + id);
    }
    public List<Auditorium> findByTheaterId(Long theaterId) {
        return audiRepo.findByTheaterId(theaterId);
    }
}
