package com.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetable.model.TimetableEntry;

@Repository
public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Long> {
}