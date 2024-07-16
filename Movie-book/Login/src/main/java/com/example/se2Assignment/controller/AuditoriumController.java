package com.example.se2Assignment.controller;

import com.example.se2Assignment.model.Auditorium;
import com.example.se2Assignment.model.Theater;
import com.example.se2Assignment.service.AuditoriumService;
import com.example.se2Assignment.service.TheaterNotFoundException;
import com.example.se2Assignment.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class AuditoriumController {
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private TheaterService theaterService;

    @GetMapping("/auditorium")
    public String showAllAuditoriumAdmin(Model model){
        List<Auditorium> auditoriums= auditoriumService.listAll();
        model.addAttribute("auditoriums",auditoriums);
        return "auditorium";
    }
    @PostMapping("/auditorium/save")
    public String saveAuditorium(Auditorium auditorium){
        auditoriumService.save(auditorium);
        return "redirect:/auditorium";
    }
    @GetMapping("/auditorium/form")
    public String showAuditoriumForm(Model model){
        List<Theater> theaters = theaterService.listAll();
        model.addAttribute("theaters",theaters);
        model.addAttribute("auditorium", new Auditorium());
        return "auditorium-form";
    }
    @GetMapping("/auditorium/edit/{id}")
    public String editAuditorium(@PathVariable("id") Long id, Model model){
        try {
            Auditorium auditorium = auditoriumService.getById(id);
            List<Theater> theaters = theaterService.listAll();
            model.addAttribute("theaters",theaters);
            model.addAttribute("auditorium", auditorium);
            return "auditorium-form";
        }
        catch (RuntimeException e){
            return "redirect:/auditorium";
        }
    }
    @GetMapping("/auditorium/delete/{id}")
    public String deleteAuditorium(@PathVariable("id") Long id) {
        try {
            auditoriumService.delete(id);
        }catch (RuntimeException e){
            e.getMessage();
        }
        return "redirect:/auditorium";
    }

}
