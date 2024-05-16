package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.service.TheaterNotFoundException;
import com.example.se2Assignment.service.TheaterService;
import com.example.se2Assignment.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class TheaterController {
    @Autowired
    private TheaterService service;
    @Autowired
    UserDetailsService userDetailsService;
    @GetMapping("/theaters")
    public String showTheaterList(Model model,RedirectAttributes ra) {
        List<Theater> listTheaters = service.listAll();
       
        model.addAttribute("listTheaters", listTheaters);
        return "Theaters";
    }
    @GetMapping("/allTheaters")
    public String showTheaterListForUser(Model model,Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        List<Theater> listTheaters = service.listAll();
        model.addAttribute("listTheaters", listTheaters);
        return "toanBoTheaters";
    }
    @GetMapping("/theaters/new")
    public String showNewForm(Model model) {
        model.addAttribute("theater", new Theater());
        model.addAttribute("pageTitle", "Add New Theater");
        return "theater_form";
    }
    @PostMapping("/theaters/save")
    public String saveTheater(Theater theater, RedirectAttributes ra) {
        service.save(theater);
        ra.addFlashAttribute("message", "The theater has been saved successfully.");
        return "redirect:/theaters";
    }

    @GetMapping("/theaters/delete/{id}")
    public String deleteTheater(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/theaters";
    }
    @GetMapping("/theaters/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Theater theater = service.get(id);
            model.addAttribute("theater", theater);
            model.addAttribute("pageTitle", "Edit Theater (ID: " + id + ")");

            return "theater_form";
        } catch (TheaterNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/theaters";
        }
    }

    @GetMapping("/theaterDetail")
    public String showTheaterDetail(@RequestParam("id") Long theaterId, Model model,Principal principal) throws TheaterNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Theater theater = service.get(theaterId);
        model.addAttribute("theater", theater);
        return "theater-detail";
    }
}



