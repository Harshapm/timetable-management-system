package com.timetable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.timetable.repository.BatchRepository;
import com.timetable.repository.SubjectRepository;
import com.timetable.repository.TeacherRepository;
import com.timetable.repository.TimeSlotRepository;
import com.timetable.repository.TimetableEntryRepository;

@Controller
public class HomeController {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final BatchRepository batchRepository;
    private final TimetableEntryRepository timetableEntryRepository;
    private final TimeSlotRepository slotRepository;

    public HomeController(TeacherRepository teacherRepository, 
                          SubjectRepository subjectRepository, 
                          BatchRepository batchRepository,
                          TimetableEntryRepository timetableEntryRepository,
                          TimeSlotRepository slotRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.batchRepository = batchRepository;
        this.timetableEntryRepository = timetableEntryRepository;
        this.slotRepository = slotRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Send counts to the dashboard
        model.addAttribute("teacherCount", teacherRepository.count());
        model.addAttribute("subjectCount", subjectRepository.count());
        model.addAttribute("batchCount", batchRepository.count());
        model.addAttribute("timetableCount", timetableEntryRepository.count());
        model.addAttribute("slotCount", slotRepository.count());
        return "home";
    }
}
