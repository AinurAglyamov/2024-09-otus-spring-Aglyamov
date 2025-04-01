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
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.services.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/api/courses")
    public ResponseEntity<Page<CourseDto>> getCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAll(pageable));
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping("/api/courses/{id}/groups")
    public ResponseEntity<List<GroupDto>> getGroupsByCourseId(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.getGroupsByCourseId(id));
    }

    @PostMapping("/api/courses")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto course) {
        return ResponseEntity.ok(courseService.create(course));
    }

    @PutMapping("/api/courses")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto course) {
        return ResponseEntity.ok(courseService.update(course));
    }

    @DeleteMapping("/api/courses/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable UUID id) {
        courseService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
