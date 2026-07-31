package com.timetable.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.timetable.model.TimetableEntry;
import com.timetable.repository.BatchRepository;
import com.timetable.repository.SubjectRepository;
import com.timetable.repository.TeacherRepository;
import com.timetable.repository.TimeSlotRepository;
import com.timetable.repository.TimetableEntryRepository;
import com.timetable.service.TimetableService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    private final TimetableEntryRepository timetableEntryRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final BatchRepository batchRepository;
    private final TimetableService timetableService;

    public TimetableController(
            TimetableEntryRepository timetableEntryRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository,
            TimeSlotRepository timeSlotRepository,
            BatchRepository batchRepository,
            TimetableService timetableService) {
        this.timetableEntryRepository = timetableEntryRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.batchRepository = batchRepository;
        this.timetableService = timetableService;
    }

    @GetMapping
    public String listTimetable(Model model) {
        model.addAttribute("entries", timetableEntryRepository.findAll());
        return "timetable/list";
    }

    @GetMapping("/new")
    public String newEntryForm(Model model) {
        model.addAttribute("entry", new TimetableEntry());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("timeSlots", timeSlotRepository.findAll());
        model.addAttribute("batches", batchRepository.findAll());
        return "timetable/form";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"timetable.csv\"");

        List<TimetableEntry> entries = timetableEntryRepository.findAll();

        // Create the CSV content safely (handle possible null associations and commas in names)
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,Teacher,Subject,Batch,Day,Period,Time\n");

        for (TimetableEntry e : entries) {
            String teacherName = e.getTeacher() != null && e.getTeacher().getName() != null ? e.getTeacher().getName().replace(",", " ") : "";
            String subjectName = e.getSubject() != null && e.getSubject().getName() != null ? e.getSubject().getName().replace(",", " ") : "";
            String batchName = e.getBatch() != null && e.getBatch().getName() != null ? e.getBatch().getName().replace(",", " ") : "";
            String day = e.getTimeSlot() != null && e.getTimeSlot().getDayOfWeek() != null ? e.getTimeSlot().getDayOfWeek() : "";
            String period = e.getTimeSlot() != null ? String.valueOf(e.getTimeSlot().getPeriodNumber()) : "";
            String timeRange = "";
            if (e.getTimeSlot() != null) {
                String start = e.getTimeSlot().getStartTime() != null ? e.getTimeSlot().getStartTime().toString() : "";
                String end = e.getTimeSlot().getEndTime() != null ? e.getTimeSlot().getEndTime().toString() : "";
                timeRange = start + (start.isEmpty() || end.isEmpty() ? "" : "-") + end;
            }

            csvContent.append(e.getId() != null ? e.getId() : "").append(",")
                    .append(teacherName).append(",")
                    .append(subjectName).append(",")
                    .append(batchName).append(",")
                    .append(day).append(",")
                    .append(period).append(",")
                    .append(timeRange).append("\n");
        }

        response.getWriter().write(csvContent.toString());
    }

    @PostMapping
    public String saveEntry(
            @ModelAttribute TimetableEntry entry,
            RedirectAttributes redirectAttributes) {

        // Get all entries once to use for checking
        java.util.List<TimetableEntry> allEntries = timetableEntryRepository.findAll();

        // 1. Check for Teacher Conflict
        boolean teacherConflict = allEntries.stream()
                .anyMatch(e -> e.getTeacher().getId().equals(entry.getTeacher().getId()) &&
                        e.getTimeSlot().getId().equals(entry.getTimeSlot().getId()));

        if (teacherConflict) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Conflict: Teacher " + entry.getTeacher().getName() + " already has a class in this time slot.");
            return "redirect:/timetable/new";
        }

        // 2. Check for Batch Conflict
        boolean batchConflict = allEntries.stream()
                .anyMatch(e -> e.getBatch().getId().equals(entry.getBatch().getId()) &&
                        e.getTimeSlot().getId().equals(entry.getTimeSlot().getId()));

        if (batchConflict) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Conflict: Batch " + entry.getBatch().getName()
                            + " already has a scheduled class in this time slot.");
            return "redirect:/timetable/new";
        }

        timetableEntryRepository.save(entry);
        return "redirect:/timetable";
    }

    // Generate endpoint
    @PostMapping("/generate")
    public String generateAutomated() {
        timetableService.generateAutomatedTimetable();
        return "redirect:/timetable";
    }

    // Clear endpoint
    @PostMapping("/clear")
    public String clearTimetable() {
        timetableService.clearTimetable();
        return "redirect:/timetable";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        timetableEntryRepository.deleteById(id);
        return "redirect:/timetable";
    }

}