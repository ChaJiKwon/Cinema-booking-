package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.Seat;
import com.example.se2Assignment.model.SeatType;
import com.example.se2Assignment.service.AuditoriumService;
import com.example.se2Assignment.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private AuditoriumService auditoriumService;

    @GetMapping("/seats")
    public String showSeatDashboard(Model model){
        List<Seat> seats = seatService.listAllSeats();
        model.addAttribute("seats",seats);

        return "seatsDashboard";
    }
    @PostMapping("/seats/save")
    public String saveSeatForm( Seat seat){
        seatService.saveSeats(seat);
        return "redirect:/seats";
    }
    @GetMapping("/seats/form")
    public String showSeatForm(Model model){
        String [] seatTypes = SeatType.names();
        List<Auditorium> auditoriums = auditoriumService.listAll();
        model.addAttribute("seatTypes", seatTypes);
        model.addAttribute("auditoriums",auditoriums);
        model.addAttribute("seat",new Seat());
        return "seatsForm";
    }
    @GetMapping("/seats/edit/{id}")
    public String editSeat(@PathVariable("id") Long id, Model model){
        try{
            Seat seat = seatService.get(id);
            String [] seatTypes = SeatType.names();
            List<Auditorium> auditoriums = auditoriumService.listAll();
            model.addAttribute("seatTypes", seatTypes);
            model.addAttribute("seat",seat);
            model.addAttribute("auditoriums",auditoriums);
            return "seatsForm";
        }
        catch (RuntimeException e){
            return "redirect:/seats";
        }
    }
    @GetMapping("/seats/delete/{id}")
    public String deleteSeat(@PathVariable("id") Long id){
        try{
            seatService.delete(id);
        }
        catch (RuntimeException e){

        }
        return "redirect:/seats";
    }
}
