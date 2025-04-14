package ru.otus.hw.school.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.models.Course;
import ru.otus.hw.school.models.Group;
import ru.otus.hw.school.models.enums.GroupStatus;
import ru.otus.hw.school.repositories.CourseRepository;
import ru.otus.hw.school.repositories.GroupRepository;
import ru.otus.hw.school.services.GroupService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<GroupDto> getAll(Pageable pageable) {
        Page<Group> groupsPage = groupRepository.findAll(pageable);
        List<GroupDto> groupDtos = groupsPage.stream()
                .map(group -> modelMapper.map(group, GroupDto.class))
                .toList();

        return new PageImpl<>(groupDtos, groupsPage.getPageable(), groupsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByGroupId(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Группа обучения с id %s не найдена".formatted(groupId)));

        return group.getStudents().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto getById(UUID id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Группа обучения с id %s не найдена".formatted(id)));

        return modelMapper.map(group, GroupDto.class);
    }

    @Override
    @Transactional
    public GroupDto create(GroupDto groupDto) {
        Group group = modelMapper.map(groupDto, Group.class);
        group.setStatus(GroupStatus.CREATED);
        group.setCreatedAt(Instant.now());
        groupRepository.save(group);

        return modelMapper.map(group, GroupDto.class);
    }

    @Override
    @Transactional
    public GroupDto update(GroupDto groupDto) {
        UUID id = groupDto.getId();
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Группа обучения с id %s не найден".formatted(id)));
        group.setGroupName(groupDto.getGroupName())
                .setStartDate(groupDto.getStartDate())
                .setEndDate(groupDto.getEndDate())
                .setStatus(groupDto.getStatus())
                .setUpdatedAt(Instant.now());

        CourseDto courseDto = groupDto.getCourse();

        if (courseDto != null) {
            Course course = courseRepository.findById(courseDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Курс с id %s не найден".formatted(courseDto.getId())));
            group.setCourse(course);
        }

        Group savedGroup = groupRepository.save(group);

        return modelMapper.map(savedGroup, GroupDto.class);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        groupRepository.deleteById(id);
    }
}
