package ru.otus.hw.school.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.HomeworkHistoryDto;
import ru.otus.hw.school.services.HomeworkService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @GetMapping("/api/homeworks")
    public ResponseEntity<Page<HomeworkDto>> getHomeworks(Pageable pageable) {
        return ResponseEntity.ok(homeworkService.getAll(pageable));
    }

    @GetMapping("/api/homeworks/{id}")
    public ResponseEntity<HomeworkDto> getHomeworkById(@PathVariable UUID id) {
        return ResponseEntity.ok(homeworkService.getById(id));
    }

    @GetMapping("/api/homeworks/{id}/history")
    public ResponseEntity<List<HomeworkHistoryDto>> getHomeworkHistoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(homeworkService.getHistory(id));
    }

    @PostMapping("/api/homeworks")
    public ResponseEntity<HomeworkDto> createHomework(@RequestBody HomeworkDto homework) {
        return ResponseEntity.ok(homeworkService.create(homework));
    }

    @DeleteMapping("/api/homeworks/{id}")
    public ResponseEntity<Void> deleteHomeworkById(@PathVariable UUID id) {
        homeworkService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
