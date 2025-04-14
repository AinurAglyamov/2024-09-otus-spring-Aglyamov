package ru.otus.hw.school.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.homeworkstatus.ChangeHomeworkStatusRequest;
import ru.otus.hw.school.models.enums.HomeworkStatus;
import ru.otus.hw.school.services.homeworkstatus.HomeworkStatusService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeworkStatusController {

    private final Map<HomeworkStatus, HomeworkStatusService> statusServices;

    @PutMapping ("/api/homeworks-status/sent")
    public ResponseEntity<HomeworkDto> send(@RequestBody ChangeHomeworkStatusRequest request) {
        return ResponseEntity.ok(statusServices.get(HomeworkStatus.SENT).change(request));
    }

    @PutMapping("/api/homeworks-status/checking")
    public ResponseEntity<HomeworkDto> checking(@RequestBody ChangeHomeworkStatusRequest request) {
        return ResponseEntity.ok(statusServices.get(HomeworkStatus.CHECKING).change(request));
    }

    @PutMapping("/api/homeworks-status/rework")
    public ResponseEntity<HomeworkDto> rework(@RequestBody ChangeHomeworkStatusRequest request) {
        return ResponseEntity.ok(statusServices.get(HomeworkStatus.REWORK).change(request));
    }

    @PutMapping("/api/homeworks-status/accepted")
    public ResponseEntity<HomeworkDto> accepted(@RequestBody ChangeHomeworkStatusRequest request) {
        return ResponseEntity.ok(statusServices.get(HomeworkStatus.ACCEPTED).change(request));
    }

}
