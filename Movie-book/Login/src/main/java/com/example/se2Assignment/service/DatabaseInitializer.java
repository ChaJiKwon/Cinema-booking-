package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.Theater;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationRunner {
    @Autowired
    private MovieService movieService;
    @Autowired
    private TheaterService theaterService;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        // Fetch all movies and theaters from the database
        List<Movie> movies = movieService.listAll();
        List<Theater> theaters = theaterService.listAll();

        // For each movie, associate it with each theater
        for (Movie movie : movies) {
            for (Theater theater : theaters) {
                movie.getTheaters().add(theater);
                movieService.save(movie);
            }
        }
    }
}
