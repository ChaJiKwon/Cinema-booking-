package com.example.se2Assignment.service;

import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.repository.MovieRepository;
import com.example.se2Assignment.repository.TheaterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repo;
    @Autowired
    private TheaterRepository theaterRepo;
    public List<Movie> listAll() {
        return (List<Movie>) repo.findAll();
    }
    public void save(Movie movie) {
        repo.save(movie);
    }
    @Transactional
    public void delete(Long id) throws MovieNotFoundException {
        Movie movie = repo.findById(id).orElseThrow(() -> new MovieNotFoundException("Could not find movie with ID " + id));

        // Remove the associations between the movie and its theaters
        movie.getTheaters().clear();
        repo.save(movie);
        
        repo.deleteById(id);
    }

    @Transactional
    public Set<Theater> getTheatersByMovieId(Long movieId) throws MovieNotFoundException {
        // Fetch the movie from the database
        Movie movie = repo.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Could not find movie with ID " + movieId));

        // Return the theaters showing the movie
        return movie.getTheaters();
    }
    public Movie get(Long id) throws MovieNotFoundException {
        Optional<Movie> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new MovieNotFoundException("Could not find any movies with ID " + id);
    }
    
    public List<Movie> searchMovieByName(String keyword) {
        return repo.findByMovieNameContainingIgnoreCase(keyword);
    }
    public List<String> getAllCategories() {
        return repo.findAllCategories();
    }
    public List<Movie> findByGenre(String genre) {
        return repo.findByGenre(genre);
    }
}