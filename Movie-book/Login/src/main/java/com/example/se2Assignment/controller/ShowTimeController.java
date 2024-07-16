package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.Movie;
import com.example.se2Assignment.model.ShowTime;
import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.service.*;
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

    @Autowired
    AuditoriumService auditoriumService;

    @GetMapping("/showTimes")
    public String getAllShowTime(Model model) {
        List<ShowTime> listShowTimes = showTimeService.listAll();
        model.addAttribute("listShowTimes", listShowTimes);
        return "allShowTimes";
    }
    @GetMapping("/showTimes/new")
    public String showNewForm(Model model) {
        model.addAttribute("showTime", new ShowTime());
        List<Auditorium> auditoriums = auditoriumService.listAll(); // Fetch all theaters
        model.addAttribute("auditoriums", auditoriums);
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
    public String deleteShowTime(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            showTimeService.delete(id);
            ra.addFlashAttribute("message", "The showTime ID " + id + " has been deleted.");
        } catch (ShowTimeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/showTimes";
    }
    @GetMapping("/showTimes/edit/{id}")
    public String editShowTime(@PathVariable("id") Long id,Model model){
        try{
            ShowTime showTime = showTimeService.get(id);
            List <Auditorium> auditoriums = auditoriumService.listAll();
            model.addAttribute("auditoriums",auditoriums);
            model.addAttribute("showTime",showTime);
            return "showTime-form";
        }
        catch (ShowTimeNotFoundException e){
            return "redirect:/showTimes";
        }
    }
}
