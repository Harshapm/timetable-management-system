package com.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timetable.model.Batch;
import com.timetable.repository.BatchRepository;

@Controller
@RequestMapping("/batches")
public class BatchController {

    private final BatchRepository batchRepository;

    public BatchController(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @GetMapping
    public String listBatches(Model model) {
        model.addAttribute("batches", batchRepository.findAll());
        return "batches/list";
    }

    @GetMapping("/new")
    public String newBatchForm(Model model) {
        model.addAttribute("batch", new Batch());
        return "batches/form";
    }

    @PostMapping
    public String saveBatch(@ModelAttribute Batch batch) {
        batchRepository.save(batch);
        return "redirect:/batches";
    }
}