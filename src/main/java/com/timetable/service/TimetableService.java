package com.timetable.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timetable.model.Batch;
import com.timetable.model.Subject;
import com.timetable.model.Teacher;
import com.timetable.model.TimeSlot;
import com.timetable.model.TimetableEntry;
import com.timetable.repository.BatchRepository;
import com.timetable.repository.SubjectRepository;
import com.timetable.repository.TeacherRepository;
import com.timetable.repository.TimeSlotRepository;
import com.timetable.repository.TimetableEntryRepository;

@Service
public class TimetableService {

    private final TimetableEntryRepository entryRepo;
    private final TeacherRepository teacherRepo;
    private final SubjectRepository subjectRepo;
    private final TimeSlotRepository slotRepo;
    private final BatchRepository batchRepo;

    public TimetableService(TimetableEntryRepository entryRepo, TeacherRepository teacherRepo,
                            SubjectRepository subjectRepo, TimeSlotRepository slotRepo, BatchRepository batchRepo) {
        this.entryRepo = entryRepo;
        this.teacherRepo = teacherRepo;
        this.subjectRepo = subjectRepo;
        this.slotRepo = slotRepo;
        this.batchRepo = batchRepo;
    }

    public void generateAutomatedTimetable() {
        List<Batch> batches = batchRepo.findAll();
        List<Subject> subjects = subjectRepo.findAll();
        List<Teacher> teachers = teacherRepo.findAll();
        List<TimeSlot> slots = slotRepo.findAll();

        for (Batch batch : batches) {
            for (Subject subject : subjects) {
                // For every batch and subject, try to find a valid slot and teacher
                boolean scheduled = false;
                
                for (TimeSlot slot : slots) {
                    if (scheduled) break;

                    for (Teacher teacher : teachers) {
                        if (!hasConflict(teacher, batch, slot)) {
                            TimetableEntry entry = new TimetableEntry();
                            entry.setBatch(batch);
                            entry.setSubject(subject);
                            entry.setTeacher(teacher);
                            entry.setTimeSlot(slot);
                            entryRepo.save(entry);
                            scheduled = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean hasConflict(Teacher teacher, Batch batch, TimeSlot slot) {
        return entryRepo.findAll().stream().anyMatch(e ->
                e.getTimeSlot().getId().equals(slot.getId()) &&
                (e.getTeacher().getId().equals(teacher.getId()) || e.getBatch().getId().equals(batch.getId()))
        );
    }
    
    public void clearTimetable() {
        entryRepo.deleteAll();
    }
}