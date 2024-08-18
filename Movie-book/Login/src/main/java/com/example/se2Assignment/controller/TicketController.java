package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.SeatKey;
import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.model.Ticket;
import com.example.se2Assignment.service.SeatService;
import com.example.se2Assignment.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TicketController  {
    @Autowired
    TicketService ticketService;
    @Autowired
    SeatService seatService;

    @GetMapping("/tickets")
    public String listTickets(Model model){
        List<Ticket> tickets = ticketService.listAll();
        model.addAttribute("tickets",tickets);
        return "ticket-dashboard";
    }

    @GetMapping("/tickets/info/{id}")
    public String showTicketInfo(Model model, @PathVariable("id") Long id){
        try {
            Ticket ticket = ticketService.getById(id);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = ticket.getEndTime().format(formatter);
            int hour = ticket.getShowTime().getShowDateTime().getHour();
            int min= ticket.getShowTime().getShowDateTime().getMinute();
            String formattedTime = hour + ":" + String.format("%02d", min); // %02d adds leading zeros
            int endHour = ticket.getEndTime().getHour();
            int endMin= ticket.getEndTime().getMinute();
            SeatKey seatKey = new SeatKey(ticket.getShowTime().getId(),ticket.getShowTime().getShowDateTime().toLocalDate());
            model.addAttribute("seatKey",seatKey);
            String formattedEndTime = endHour + ":" + String.format("%02d", endMin);
            model.addAttribute("time",formattedTime);
            model.addAttribute("endtime",formattedEndTime);
            model.addAttribute("date",formattedDate);
            model.addAttribute("ticket", ticket);
            return "ticket-info-admin";
        }
        catch (RuntimeException e){
            return "redirect:/tickets";
        }
    }
}
