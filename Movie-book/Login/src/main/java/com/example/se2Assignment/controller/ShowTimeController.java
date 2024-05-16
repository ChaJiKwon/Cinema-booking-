package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.ShowTime;
import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.service.ShowTimeNotFoundException;
import com.example.se2Assignment.service.ShowTimeService;
import com.example.se2Assignment.service.TheaterNotFoundException;
import com.example.se2Assignment.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShowTimeController {
@Autowired  TheaterService theaterService;
@Autowired ShowTimeService showTimeService;

    @GetMapping("/showTimes")
    public String getAllShowTime(Model model) {
        List<ShowTime> listShowTimes = showTimeService.listAll();
        model.addAttribute("listShowTimes", listShowTimes);
        return "allShowTimes";
    }
    @GetMapping("/showTimes/new")
    public String showNewForm(Model model) {
        model.addAttribute("showTime", new ShowTime());
        List<Theater> theaters = theaterService.listAll(); // Fetch all theaters
        model.addAttribute("theaters", theaters);
        model.addAttribute("pageTitle", "Add New ShowTime");
        return "showTime-form";
    }
    @PostMapping("/showTimes/save")
    public String saveShowTime(ShowTime showTime, RedirectAttributes ra) {
        showTimeService.save(showTime);
        ra.addFlashAttribute("message", "The showTime has been saved successfully.");
        return "redirect:/showTimes";
    }

    @GetMapping("/showTimes/delete/{id}")
    public String deleteTheater(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            showTimeService.delete(id);
            ra.addFlashAttribute("message", "The showTime ID " + id + " has been deleted.");
        } catch (ShowTimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/showTimes";
    }
}
