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
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.services.StudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/api/students")
    public ResponseEntity<Page<StudentDto>> getStudents(Pageable pageable) {
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping("/api/students/{id}/groups")
    public ResponseEntity<List<GroupDto>> getGroupsByStudentId(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getGroupsByStudentId(id));
    }

    @GetMapping("/api/students/{id}/homeworks")
    public ResponseEntity<List<HomeworkDto>> getHomeworksByStudentId(@PathVariable UUID id) {
        return ResponseEntity.ok(studentService.getHomeworksByStudentId(id));
    }

    @PostMapping("/api/students")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @PutMapping("/api/students")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto student) {
        return ResponseEntity.ok(studentService.update(student));
    }

    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable UUID id) {
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
