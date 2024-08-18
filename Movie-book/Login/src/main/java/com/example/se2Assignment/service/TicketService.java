package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.Ticket;
import com.example.se2Assignment.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    public Ticket getById(Long id){
        Optional<Ticket> result = ticketRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new RuntimeException("Could not find any with ID " + id);
    }
    public List<Ticket> listAll(){
        return ticketRepository.findAll();
    }
    public void save(Ticket ticket){
        ticketRepository.save(ticket);
    }
    @Transactional
    public void delete(Long id) throws RuntimeException{
        ticketRepository.deleteById(id);
    }
}
