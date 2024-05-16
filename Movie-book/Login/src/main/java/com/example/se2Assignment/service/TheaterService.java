package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.repository.TheaterRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository repo;
    @Autowired
    private EntityManager entityManager;
    public List<Theater> listAll() {
        return (List<Theater>) repo.findAll();
    }
    public void save(Theater theater) {
        repo.save(theater);
    }
    @Transactional
    public void delete(Long id) throws TheaterNotFoundException {
        Theater theater = repo.findById(id).orElseThrow(() -> new TheaterNotFoundException("Could not find theater with ID " + id));

        // Remove the associations between the theater and its movies in the movie_theater table
        entityManager.createNativeQuery("DELETE FROM movie_theater WHERE theater_id = ?")
                .setParameter(1, id)
                .executeUpdate();
        
        repo.deleteById(id);
    }

    public Theater get(Long id) throws TheaterNotFoundException {
        Optional<Theater> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TheaterNotFoundException("Could not find theater with ID " + id);
    }
}
