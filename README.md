# Timetable Management System

A beginner-level academic project that implements a basic timetable management system for scheduling classes.  
Built with Java, Spring Boot, MySQL, and Thymeleaf.

🔗 Live Repo: https://github.com/harshapm/timetable-management-system

---

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot (Spring MVC, Spring Data JPA)
- **Database:** MySQL
- **Frontend:** Thymeleaf + HTML/CSS
- **Build Tool:** Maven
- **Tools:** Git, GitHub

---

## Features

- Manage **Teachers** (name, email, department)
- Manage **Subjects** (code, name, credits)
- Manage **Time Slots** (day, period, start/end time)
- Create **Timetable Entries** (teacher + subject + time slot)
- Basic **conflict detection**:
  - Prevents assigning the same teacher to the same time slot more than once

---

## Project Architecture

This is a typical layered Spring Boot web application:

- **Controller Layer** (`com.timetable.controller`)
  - `HomeController`
  - `TeacherController`
  - `SubjectController`
  - `TimeSlotController`
  - `TimetableController`
  - Handles HTTP requests, prepares model data, returns Thymeleaf views.

- **Service Logic** (minimal; mostly in controllers for this academic project)
  - Conflict detection logic for timetable entries is implemented in `TimetableController`.

- **Repository Layer** (`com.timetable.repository`)
  - `TeacherRepository`
  - `SubjectRepository`
  - `TimeSlotRepository`
  - `TimetableEntryRepository`
  - Extend `JpaRepository` for CRUD operations.

- **Model Layer** (`com.timetable.model`)
  - `Teacher`
  - `Subject`
  - `TimeSlot`
  - `TimetableEntry`
  - JPA entities mapped to database tables.

- **View Layer** (`src/main/resources/templates`)
  - `home.html`
  - `teachers/list.html`, `teachers/form.html`
  - `subjects/list.html`, `subjects/form.html`
  - `time-slots/list.html`, `time-slots/form.html`
  - `timetable/list.html`, `timetable/form.html`

Request flow (simplified):

```text
Browser → Controller → Repository → MySQL
Browser ← Controller (Thymeleaf view) ←
```

---

## Database Schema

Database: `timetable_db`

### Tables

1. **teachers**

   - `id` (BIGINT, PK, AUTO_INCREMENT)
   - `name` (VARCHAR)
   - `email` (VARCHAR)
   - `department` (VARCHAR)

2. **subjects**

   - `id` (BIGINT, PK, AUTO_INCREMENT)
   - `code` (VARCHAR)
   - `name` (VARCHAR)
   - `credits` (INT)

3. **time_slots**

   - `id` (BIGINT, PK, AUTO_INCREMENT)
   - `day_of_week` (VARCHAR) — e.g., MONDAY, TUESDAY
   - `period_number` (INT)
   - `start_time` (VARCHAR) — e.g., "09:00"
   - `end_time` (VARCHAR) — e.g., "09:50"

4. **timetable_entries**

   - `id` (BIGINT, PK, AUTO_INCREMENT)
   - `teacher_id` (BIGINT, FK → teachers.id)
   - `subject_id` (BIGINT, FK → subjects.id)
   - `time_slot_id` (BIGINT, FK → time_slots.id)

Relationships:

- `timetable_entries.teacher_id` → `teachers.id` (many-to-one)
- `timetable_entries.subject_id` → `subjects.id` (many-to-one)
- `timetable_entries.time_slot_id` → `time_slots.id` (many-to-one)

---

## How to Run

### Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)
- MySQL Server

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/harshapm/timetable-management-system.git
   cd timetable-management-system
   ```

2. Create a MySQL database:

   ```sql
   CREATE DATABASE timetable_db;
   ```

3. Configure database credentials:

   In `src/main/resources/application.properties`, update:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/timetable_db?useSSL=false&serverTimezone=Asia/Kolkata
   spring.datasource.username=root
   spring.datasource.password=YOUR_ROOT_PASSWORD
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

   or use:

   ```bash
   ./mvnw spring-boot:run
   ```

5. Open in browser:

   http://localhost:8080

---

## API / Endpoints (Web Pages)

- Home: `/`
- Teachers: `/teachers`
- Subjects: `/subjects`
- Time Slots: `/time-slots`
- Timetable: `/timetable`

All pages are server-rendered using Thymeleaf.

---

## Future Enhancements (Optional)

- Add **Batch/Student Group** entity and include it in scheduling.
- Improve conflict detection (room conflicts, batch conflicts, teacher availability).
- Add authentication & authorization (login, roles).
- Export timetable as PDF/CSV.
- Add a simple REST API layer for frontend frameworks.

---

## Note

This is an **academic project** focused on learning:

- Spring Boot basics
- JPA/Hibernate with MySQL
- MVC pattern with Thymeleaf
- Basic web development and conflict detection logic

It is intentionally kept simple to prioritize learning over production readiness.
