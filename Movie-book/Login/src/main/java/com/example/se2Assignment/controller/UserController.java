package com.example.se2Assignment.controller;

import com.example.se2Assignment.dto.UserDto;
import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.service.MovieService;
import com.example.se2Assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    UserDetailsService userDetailsService;
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
    @GetMapping("user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        List<Movie> movies = movieService.listAll();
        model.addAttribute("movies", movies);
        return "user";
    }
    @GetMapping("admin-page")
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }
}
