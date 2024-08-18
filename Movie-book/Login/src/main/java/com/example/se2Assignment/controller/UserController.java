package com.example.se2Assignment.controller;

import com.example.se2Assignment.dto.UserDto;
import com.example.se2Assignment.model.*;
import com.example.se2Assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    SeatService seatService;

    @Autowired
    TicketService ticketService;
    @Autowired
    SeatKeyService seatKeyService;
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto){
        return "register";
    }
    @GetMapping("/hello")
    public String helloPage(){
        return "hello";
    }
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user")UserDto userDto, Model model){
        userService.save(userDto);
        model.addAttribute("message","register successfully");
        return "register";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        for (Ticket ticket :ticketService.listAll()){
            for (Seat seat: ticket.getSeats()){
                SeatKey seatKey = new SeatKey(ticket.getShowTime().getId(),ticket.getShowTime().getShowDateTime().toLocalDate());
                if (LocalDateTime.now().isAfter(ticket.getEndTime())){
                    seat.getSeatStatus().put(seatKey,true);
                }
                seatKeyService.save(seatKey);
                seatService.saveSeats(seat);
            }
        }
        List<Movie> movies = movieService.listAll();
        model.addAttribute("movies", movies);
        return "user";
    }

    @GetMapping("/user-tickets")
    public String showUserTickets(Model model,Principal principal){
        List<String> showTimes= new ArrayList<>();
        List<String> endTimes= new ArrayList<>();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        User user = userDetailsService.getUserByUsername(principal.getName());
        Set<Ticket> tickets = user.getTickets();
        List<Ticket> bookedTickets= new ArrayList<>();
        for (Ticket ticket : tickets) {
            boolean allSeatsbooked = false;
            for (Seat seat : ticket.getSeats()) {
                SeatKey seatKey = new SeatKey(ticket.getShowTime().getId(), ticket.getShowTime().getShowDateTime().toLocalDate());
                if (!seat.getSeatStatus().get(seatKey)) {
                    allSeatsbooked = true;
                    break; // Exit the loop if any seat is booked
                }
            }
            if (allSeatsbooked) {
                bookedTickets.add(ticket);
                int hour = ticket.getShowTime().getShowDateTime().getHour();
                int min = ticket.getShowTime().getShowDateTime().getMinute();
                int endHour = ticket.getEndTime().getHour();
                int endMin= ticket.getEndTime().getMinute();

                String formattedTime = hour + ":" + String.format("%02d", min); // %02d adds leading zeros
                String formattedEndTime = endHour + ":" + String.format("%02d", endMin);
                showTimes.add(formattedTime);
                endTimes.add(formattedEndTime);
            }

        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        model.addAttribute("formatter",formatter);
        model.addAttribute("showtimes",showTimes);
        model.addAttribute("endtimes",endTimes);

        model.addAttribute("bookedTicket",bookedTickets);
        model.addAttribute("num",tickets.size());
        return "user-tickets";
    }

    @GetMapping("/user-tickets/cancel/{id}")
    public String cancelTicket(@PathVariable("id") Long id){
        System.out.println(id);
        Ticket ticket = ticketService.getById(id);
        SeatKey seatKey = new SeatKey(ticket.getShowTime().getId(), ticket.getShowTime().getShowDateTime().toLocalDate());
        for (Seat seat : ticket.getSeats()){
            seat.getSeatStatus().put(seatKey,true);
            seatService.saveSeats(seat);
        }
        return "redirect:/user-tickets?canceled=true";
    }
    @GetMapping("admin-page")
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }
}
