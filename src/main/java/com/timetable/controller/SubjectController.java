package com.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timetable.model.Subject;
import com.timetable.repository.SubjectRepository;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "subjects/list";
    }

    @GetMapping("/new")
    public String newSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/form";
    }

    @PostMapping
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }
}