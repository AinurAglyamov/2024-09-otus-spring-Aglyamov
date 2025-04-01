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
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.services.GroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/api/groups")
    public ResponseEntity<Page<GroupDto>> getGroups(Pageable pageable) {
        return ResponseEntity.ok(groupService.getAll(pageable));
    }

    @GetMapping("/api/groups/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @GetMapping("/api/groups/{id}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByGroupId(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getStudentsByGroupId(id));
    }

    @PostMapping("/api/groups")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto group) {
        return ResponseEntity.ok(groupService.create(group));
    }

    @PutMapping("/api/groups")
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto group) {
        return ResponseEntity.ok(groupService.update(group));
    }

    @DeleteMapping("/api/groups/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable UUID id) {
        groupService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
