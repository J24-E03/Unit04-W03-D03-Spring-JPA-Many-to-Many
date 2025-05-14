package com.dci.full_mvc.controller;

import com.dci.full_mvc.model.Director;
import com.dci.full_mvc.model.Movie;
import com.dci.full_mvc.repository.DirectorRepository;
import com.dci.full_mvc.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;


    @GetMapping
    public String allMovies(Model model){

        model.addAttribute("movies",movieRepository.findAll());
        return "movies/movies-list";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable Long id,Model model){
        Movie foundMovie = movieRepository.findById(id)
                        .orElseThrow(()->new RuntimeException("Movie not found"));
        model.addAttribute("movie",foundMovie);
        return "movies/movie-details";
    }


    @GetMapping("/new")
    public String createMovie(Model model){

        model.addAttribute("movie", new Movie());
        model.addAttribute("directors",directorRepository.findAll());

        return "movies/movie-form";
    }

    @PostMapping("/create")
    public String createNewMovie(@ModelAttribute Movie movie, Model model){

//        validation for the director
        Director director = directorRepository.findById(movie.getDirector().getDirectorId())
                        .orElseThrow(()->new RuntimeException("Director with Id not found"));

        movie.setDirector(director);

        Movie createdMovie = movieRepository.save(movie);

        return "redirect:/movies/" + createdMovie.getId();
    }

    @GetMapping("/update/{id}")
    public String updateMovieForm(@PathVariable Long id, Model model){
        Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Movie Not Found"));
        model.addAttribute("movie",foundMovie);
        model.addAttribute("directors",directorRepository.findAll());

        return "movies/movie-form";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@ModelAttribute Movie movie){
        System.out.println(movie);
        //        validation for the director
        Director director = directorRepository.findById(movie.getDirector().getDirectorId())
                .orElseThrow(()->new RuntimeException("Director with Id not found"));

        movie.setDirector(director);


        movieRepository.save(movie);

        return "redirect:/movies/" + movie.getId();
    }

//
    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);

        return "redirect:/movies";
    }
}
