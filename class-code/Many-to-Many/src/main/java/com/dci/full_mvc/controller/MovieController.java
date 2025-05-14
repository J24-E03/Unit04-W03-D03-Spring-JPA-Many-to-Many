package com.dci.full_mvc.controller;

import com.dci.full_mvc.model.Director;
import com.dci.full_mvc.model.Genre;
import com.dci.full_mvc.model.Movie;
import com.dci.full_mvc.repository.DirectorRepository;
import com.dci.full_mvc.repository.GenreRepository;
import com.dci.full_mvc.repository.MovieRepository;
import com.dci.full_mvc.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final MovieService movieService;


    @GetMapping
    public String allMovies(Model model){

        model.addAttribute("movies",movieRepository.findAll());
        return "movies/movies-list";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable Long id,Model model){
        model.addAttribute("movie",movieService.findById(id));
        return "movies/movie-details";
    }


    @GetMapping("/new")
    public String createMovie(Model model){

        model.addAttribute("movie", new Movie());
        model.addAttribute("directors",directorRepository.findAll());
        model.addAttribute("genres",genreRepository.findAll());

        return "movies/movie-form";
    }

    @PostMapping("/create")
    public String createNewMovie(@ModelAttribute Movie movie, @RequestParam List<Long> genreIds){
        Movie createdMovie = movieService.createMovie(movie,genreIds);
        return "redirect:/movies/" + createdMovie.getId();
    }

    @GetMapping("/update/{id}")
    public String updateMovieForm(@PathVariable Long id, Model model){

        model.addAttribute("movie",movieService.findById(id));
        model.addAttribute("directors",directorRepository.findAll());

        return "movies/movie-form";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@ModelAttribute Movie movie){
       movieService.updateMovie(movie);
        return "redirect:/movies/" + movie.getId();
    }

//
    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);

        return "redirect:/movies";
    }

    @PostMapping("/omar")
    public String omarRoute(@RequestParam String name, @RequestParam String occupation){
        System.out.println(name);
        System.out.println(occupation);

        return "redirect:/movies";
    }
}
