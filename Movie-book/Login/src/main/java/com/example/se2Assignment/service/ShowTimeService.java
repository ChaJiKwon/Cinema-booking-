package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.ShowTime;
import com.example.se2Assignment.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowTimeService {
  @Autowired  ShowTimeRepository repo;
    public List<ShowTime> listAll() {
        return (List<ShowTime>) repo.findAll();
    }

    public void save(ShowTime showTime) {
        repo.save(showTime);
    }
    public void delete(Long id) throws ShowTimeNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ShowTimeNotFoundException("Could not find any showTimes with ID " + id);
        }
        repo.deleteById(id);
    }
    public ShowTime get(Long id) throws ShowTimeNotFoundException {
        Optional<ShowTime> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ShowTimeNotFoundException("Could not find any showTimes with ID " + id);
    }
}
