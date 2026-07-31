package com.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timetable.model.Teacher;
import com.timetable.repository.TeacherRepository;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "teachers/list";
    }

    @GetMapping("/new")
    public String newTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teachers/form";
    }

    @PostMapping
    public String saveTeacher(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }
}