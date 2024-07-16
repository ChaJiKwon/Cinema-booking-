package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.*;
import com.example.se2Assignment.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class MovieController {
    @Autowired
    private MovieService service;
    @Autowired
    private UserService userService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    ShowTimeService showTimeService;
    @Autowired
    SeatService seatService;
    @GetMapping("/movies")
    public String showMovieList(Model model) {
        List<Movie> listMovies = service.listAll();
        model.addAttribute("listMovies", listMovies);
        return "movies";
    }
    @GetMapping("/movies/new")
    public String showNewForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("pageTitle", "Add New Movie");
        return "movie_form";
    }
    @PostMapping("/movies/save")
    public String saveMovie(Movie movie, RedirectAttributes ra) {
        service.save(movie);
        ra.addFlashAttribute("message", "The movie has been saved successfully.");
        return "redirect:/movies";
    }
    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The movie ID " + id + " has been deleted.");
        } catch (MovieNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/movies";
    }

    @GetMapping("/movies/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Movie movie = service.get(id);
            model.addAttribute("movie", movie);
            model.addAttribute("pageTitle", "Edit movie (ID: " + id + ")");

            return "movie_form";
        } catch (MovieNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/movies";
        }
    }
    @GetMapping("/search")
    public String searchMovieByName(@RequestParam("keyword") String keyword, Model model,Principal principal) {
        List<Movie> movies = service.searchMovieByName(keyword);
        model.addAttribute("movies", movies);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "search-results";
    }

    @GetMapping("/showAllCategory")
    public String showCategories(Model model,Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        List<String> categories = service.getAllCategories();
        model.addAttribute("categories", categories);
        return "film-category";
    }

    @GetMapping("/showAllCategory/{category}")
    public String showMoviesByCategory(@PathVariable("category") String category, Model model,Principal principal) {
        List<Movie> movies = service.findByGenre(category);
        model.addAttribute("category", category);
        model.addAttribute("movies", movies);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);

        return "movie-list";
    }

    @GetMapping("/movie-description/{id}")
    public String showMovieDescription(@PathVariable("id") Long id, Model model,
                                       RedirectAttributes ra, Principal principal) {
        try {
        Movie movie = service.get(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("movie", movie);
            return "movie-description";
        } catch (MovieNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "movie-list";
        }
    }
    @GetMapping("/movie-description/{id}/bookTheater")
    public String bookTicket(@PathVariable("id") Long id, Model model, RedirectAttributes ra,Principal principal) {
        try {
            Movie movie = service.get(id);
            Set<Theater> theaters = movie.getTheaters();
            model.addAttribute("theaters", theaters);
            model.addAttribute("movie", movie);
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
            return "theater_list";
        } catch (MovieNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/movies";
        }
    }


    @GetMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime")
    public String showShowTimeToUser(@PathVariable("movieId") Long movieId,
                                     @PathVariable("theaterId") Long theaterId,
                                     Model model,
                                     Principal principal)
            throws TheaterNotFoundException, MovieNotFoundException, ShowTimeNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Movie movie = service.get(movieId);
        model.addAttribute("user", userDetails);
        model.addAttribute("user", userDetails);
        Theater theater = theaterService.get(theaterId);
        model.addAttribute("movie", movie);
        model.addAttribute("theater", theater);
        return "showTimePage";
    }

    @GetMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime/{showtimeId}")
    public String bookSeat(@PathVariable("movieId") Long movieId, @PathVariable("theaterId") Long theaterId,
                              @PathVariable("showtimeId") Long showtimeId, Model model, RedirectAttributes ra) {
        try {
            Movie movie = service.get(movieId);
            Theater theater = theaterService.get(theaterId);
            ShowTime showTime= showTimeService.get(showtimeId);
            Auditorium auditorium = showTime.getAuditorium();
            List<Seat> seats = auditorium.getSeats();
            List<Seat> regulars = getSeatsType(seats,"REGULAR");
            List<Seat> vip = getSeatsType(seats,"VIP");
            List<Seat> vvip = getSeatsType(seats,"VVIP");


            model.addAttribute("auditorium",auditorium);
            model.addAttribute("regulars",regulars);
            model.addAttribute("vips",vip);
            model.addAttribute("vvips",vvip);
            model.addAttribute("showtime", showTime);
            model.addAttribute("movie", movie);
            model.addAttribute("theater", theater);
            return "seatBooking";
        } catch (MovieNotFoundException | TheaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/movies";
        } catch (ShowTimeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Seat> getSeatsType(List<Seat> seats,String type){
        List<Seat> result= new ArrayList<>();
        for (Seat seat: seats ){
            if (Objects.equals(seat.getType(), type)){
                result.add(seat);
            }
        }
        result.sort(Comparator.comparing(Seat::getNumber));
        return result;
    }

    @PostMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime/{showtimeId}/confirmation")
    public String processTicketSelection(@PathVariable("movieId") Long movieId,
                                         @PathVariable("theaterId") Long theaterId ,
                                         Model model,
                                         @PathVariable("showtimeId") Long showtimeId,
                                         @RequestParam(value = "seatIds") List<Long> seatIds) {
        try {
            Movie movie = service.get(movieId);
            Theater theater = theaterService.get(theaterId);
            ShowTime showTime= showTimeService.get(showtimeId);
            Auditorium auditorium = showTime.getAuditorium();
            List<Seat> seats = auditorium.getSeats();
            List<Seat> regulars = getSeatsType(seats,"REGULAR");
            List<Seat> vip = getSeatsType(seats,"VIP");
            List<Seat> vvip = getSeatsType(seats,"VVIP");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = showTime.getShowDateTime().format(formatter);
            model.addAttribute("showDate",formattedDate);
            int hour = showTime.getShowDateTime().getHour();
            int min= showTime.getShowDateTime().getMinute();
            String formattedTime = hour + ":" + String.format("%02d", min); // %02d adds leading zeros
            model.addAttribute("time",formattedTime);
            List<Seat> selectedSeats = seatService.findAllById(seatIds);
            model.addAttribute("selectedSeats",selectedSeats);
            model.addAttribute("auditorium",auditorium);
            model.addAttribute("regulars",regulars);
            model.addAttribute("vips",vip);
            model.addAttribute("vvips",vvip);
            model.addAttribute("auditorium",auditorium);

            model.addAttribute("movie", movie);
            model.addAttribute("theater", theater);
            return "ticket-confirmation";
        } catch (MovieNotFoundException | ShowTimeNotFoundException e) {

            return "redirect:/movies";
        } catch (TheaterNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
