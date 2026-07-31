package com.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timetable.model.TimeSlot;
import com.timetable.repository.TimeSlotRepository;

@Controller
@RequestMapping("/time-slots")
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping
    public String listTimeSlots(Model model) {
        model.addAttribute("timeSlots", timeSlotRepository.findAll());
        return "time-slots/list";
    }

    @GetMapping("/new")
    public String newTimeSlotForm(Model model) {
        model.addAttribute("timeSlot", new TimeSlot());
        return "time-slots/form";
    }

    @PostMapping
    public String saveTimeSlot(@ModelAttribute TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
        return "redirect:/time-slots";
    }
}