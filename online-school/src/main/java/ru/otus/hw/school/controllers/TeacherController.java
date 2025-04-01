package ru.otus.hw.school.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.services.TeacherService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/api/teachers")
    public ResponseEntity<Page<TeacherDto>> getTeachers(Pageable pageable) {
        return ResponseEntity.ok(teacherService.getAll(pageable));
    }

    @GetMapping("/api/teachers/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable UUID id) {
        return ResponseEntity.ok(teacherService.getById(id));
    }

    @GetMapping("/api/teachers/{id}/homeworks")
    public ResponseEntity<List<HomeworkDto>> getHomeworksByTeacherId(@PathVariable UUID id) {
        return ResponseEntity.ok(teacherService.getHomeworksByTeacherId(id));
    }

    @PostMapping("/api/teachers")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacher) {
        return ResponseEntity.ok(teacherService.create(teacher));
    }

    @PutMapping("/api/teachers")
    public ResponseEntity<TeacherDto> updateTeacher(@RequestBody TeacherDto teacher) {
        return ResponseEntity.ok(teacherService.update(teacher));
    }

    @DeleteMapping("/api/teachers/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable UUID id) {
        teacherService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
