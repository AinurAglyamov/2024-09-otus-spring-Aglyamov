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
import ru.otus.hw.school.dto.HomeworkInfoDto;
import ru.otus.hw.school.services.HomeworkInfoService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HomeworkInfoController {

    private final HomeworkInfoService homeworkInfoService;

    @GetMapping("/api/homeworks-info")
    public ResponseEntity<Page<HomeworkInfoDto>> getHomeworksInfo(Pageable pageable) {
        return ResponseEntity.ok(homeworkInfoService.getAll(pageable));
    }

    @GetMapping("/api/homeworks-info/{id}")
    public ResponseEntity<HomeworkInfoDto> getHomeworkInfoById(@PathVariable UUID id) {
        return ResponseEntity.ok(homeworkInfoService.getById(id));
    }

    @PostMapping("/api/homeworks-info")
    public ResponseEntity<HomeworkInfoDto> createHomeworkInfo(@RequestBody HomeworkInfoDto homework) {
        return ResponseEntity.ok(homeworkInfoService.create(homework));
    }

    @PutMapping("/api/homeworks-info")
    public ResponseEntity<HomeworkInfoDto> updateHomeworkInfo(@RequestBody HomeworkInfoDto homework) {
        return ResponseEntity.ok(homeworkInfoService.update(homework));
    }

    @DeleteMapping("/api/homeworks-info/{id}")
    public ResponseEntity<Void> deleteHomeworkInfoById(@PathVariable UUID id) {
        homeworkInfoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
