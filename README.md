# 🗓️ Smart Timetable & Conflict Resolver
A full-stack Java application designed to automate university scheduling and prevent resource conflicts.

## Elevator pitch (resume-friendly)
Smart Timetable is a Spring Boot application that generates conflict-free academic schedules using an automated greedy scheduler and enforces conflict checks for teachers and student groups. It provides a lightweight admin UI for managing resources (teachers, subjects, batches, time slots), supports CSV export, and can be deployed quickly for demonstrations.

Example bullet for your resume:
- Built a Spring Boot (Java 21) scheduling app that auto-generates conflict-free timetables, enforces teacher/batch constraints, and exports schedules to CSV; implemented persistence with Spring Data JPA and a responsive Thymeleaf admin UI.

## 🚀 Key Features
- Automated Scheduling: a greedy algorithm that generates a complete timetable based on available teachers, subjects, and batches.
- Conflict Detection: real-time validation preventing double-booking of teachers or student groups (Batches) in the same time slot.
- Admin Dashboard: UI showing real-time statistics of academic resources.
- Data Export: export the generated timetable to a CSV file (downloadable).
- Full CRUD: manage Teachers, Subjects, Batches, Time Slots, and Timetable entries.

## 🛠️ Tech Stack
- Backend: Java 21, Spring Boot 4, Spring Data JPA
- Database: MySQL (can be swapped to H2 for quick demos)
- Frontend: Thymeleaf, Bootstrap 5
- Build tool: Maven (includes mvnw wrapper)

## Prerequisites
- Java 21 (JDK)
- MySQL (or change to H2 for local demo)
- Git (optional)

## Quick start (local)
1. Create the MySQL database and user (example):
   - CREATE DATABASE timetable_db; CREATE USER 'timetable'@'localhost' IDENTIFIED BY 'your_password'; GRANT ALL ON timetable_db.* TO 'timetable'@'localhost';
2. Update src/main/resources/application.properties with your database credentials (spring.datasource.username/password).
3. Build and run with the included wrapper (Windows):
   - mvnw.cmd -DskipTests package
   - java -jar target\timetable-0.0.1-SNAPSHOT.jar
4. Open http://localhost:8080 in your browser.

Tip: For quick demos without MySQL, switch to H2 by adding the H2 dependency and updating application.properties (I can add a demo profile if you want).

## Development notes & suggestions
- The CSV export endpoint now handles missing associations and escapes commas in names.
- Consider adding repository-level queries (e.g., findByTeacherIdAndTimeSlotId) and DB-unique constraints to avoid race conditions when multiple users schedule concurrently.
- Add @Valid and BindingResult checks on form submissions to surface validation errors in the UI.

## Demo assets
Place screenshots or a short GIF of the dashboard and timetable list in this README under a /docs or /assets folder for your portfolio.

---
If you'd like, I can:
- Add a demo profile using H2 so reviewers can run the app without MySQL (recommended for a resume/demo),
- Run a quick static code review and produce a concise list of improvements, or
- Generate a polished README.md PR-ready for your portfolio website.
