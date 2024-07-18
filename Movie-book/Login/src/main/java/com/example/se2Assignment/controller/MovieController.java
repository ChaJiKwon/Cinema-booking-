package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.*;
import com.example.se2Assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.Principal;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;
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
        List<Movie> listMovies = movieService.listAll();
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
        List<Theater> theaters = theaterService.listAll();
        movie.setTheaters(theaters);
        movieService.save(movie);

        ra.addFlashAttribute("message", "The movie has been saved successfully.");
        return "redirect:/movies";
    }
    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            movieService.delete(id);
            ra.addFlashAttribute("message", "The movie ID " + id + " has been deleted.");
        } catch (MovieNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/movies";
    }

    @GetMapping("/movies/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Movie movie = movieService.get(id);
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
        List<Movie> movies = movieService.searchMovieByName(keyword);

        model.addAttribute("movies", movies);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "search-results";
    }

    @GetMapping("/showAllCategory")
    public String showCategories(Model model,Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        List<String> categories = movieService.getAllCategories();
        model.addAttribute("categories", categories);
        return "film-category";
    }

    @GetMapping("/showAllCategory/{category}")
    public String showMoviesByCategory(@PathVariable("category") String category, Model model,Principal principal) {
        List<Movie> movies = movieService.findByGenre(category);
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
        Movie movie = movieService.get(id);
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
            Movie movie = movieService.get(id);

            List<Theater> theaters = theaterService.listAll();
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
        Movie movie = movieService.get(movieId);
        model.addAttribute("user", userDetails);
        model.addAttribute("user", userDetails);
        Theater theater = theaterService.get(theaterId);
        model.addAttribute("movie", movie);
        model.addAttribute("theater", theater);
        return "showTimePage";
    }
    @GetMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime/{showtimeId}/date")
    public String chooseDate(@PathVariable("movieId") Long movieId,
                             @PathVariable("theaterId") Long theaterId,
                             @PathVariable("showtimeId") Long showtimeId,
                             Model model,
                             Principal principal
                             ) throws MovieNotFoundException, TheaterNotFoundException, ShowTimeNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Movie movie = movieService.get(movieId);
        ShowTime showTime= showTimeService.get(showtimeId);
        model.addAttribute("user", userDetails);
        model.addAttribute("user", userDetails);
        Theater theater = theaterService.get(theaterId);
        model.addAttribute("movie", movie);
        model.addAttribute("theater", theater);
        model.addAttribute("showtime", showTime);


        return "SelectDateTime";
    }
    @PostMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime/{showtimeId}/date/seats")
    public String bookSeat(@PathVariable("movieId") Long movieId,
                           @PathVariable("theaterId") Long theaterId,
                           @PathVariable("showtimeId") Long showtimeId,
                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           Model model,
                           RedirectAttributes ra) {
        try {

            Movie movie = movieService.get(movieId);
            Theater theater = theaterService.get(theaterId);
            ShowTime showTime= showTimeService.get(showtimeId);
            Auditorium auditorium = showTime.getAuditorium();
            List<Seat> seats = auditorium.getSeats();
            List<Seat> regulars = getSeatsType(seats,"REGULAR");
            List<Seat> vip = getSeatsType(seats,"VIP");
            List<Seat> vvip = getSeatsType(seats,"VVIP");
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year= date.getYear();
            LocalDateTime showDate= showTime.getShowDateTime().withDayOfMonth(day).withMonth(month).withYear(year);
            showTime.setShowDateTime(showDate);
            showTimeService.save(showTime);
            model.addAttribute("date",date);
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

    @PostMapping("/movie-description/{movieId}/bookTheater/{theaterId}/userShowTime/{showtimeId}/date/seats/confirmation")
    public String processTicketSelection(@PathVariable("movieId") Long movieId,
                                         @PathVariable("theaterId") Long theaterId ,
                                         Model model,
                                         @PathVariable("showtimeId") Long showtimeId,
                                         @RequestParam(value = "seatIds") List<Long> seatIds) {
        try {
            Movie movie = movieService.get(movieId);
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

            double reg_price =55000;
            double vip_price= 75000;
            double vvip_price=100000;
            double total=0;
            List<Seat> selectedSeats = seatService.findAllById(seatIds);
            for (Seat seat: selectedSeats){
                if (Objects.equals(seat.getType(), "REGULAR")){
                    total+=reg_price;
                } else if (Objects.equals(seat.getType(), "VIP")) {
                    total+=vip_price;
                }
                else if (Objects.equals(seat.getType(), "VVIP")) {
                    total+=vvip_price;
                }
            }
            model.addAttribute("price",total);
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
