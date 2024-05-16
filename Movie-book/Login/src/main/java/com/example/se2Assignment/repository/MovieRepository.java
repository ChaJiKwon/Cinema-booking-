package com.example.se2Assignment.repository;

import com.example.se2Assignment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    public Long countById(Long id);
    List<Movie> findByMovieNameContainingIgnoreCase(@Param("keyword") String keyword);
    @Query("SELECT DISTINCT m.genre FROM Movie m")
    List<String> findAllCategories();
    List<Movie> findByGenre(String genre);
}
