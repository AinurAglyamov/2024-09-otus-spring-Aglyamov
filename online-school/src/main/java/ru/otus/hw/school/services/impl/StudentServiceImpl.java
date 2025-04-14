package ru.otus.hw.school.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.StudentDto;
import ru.otus.hw.school.models.Group;
import ru.otus.hw.school.models.Homework;
import ru.otus.hw.school.models.Student;
import ru.otus.hw.school.repositories.GroupRepository;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.StudentRepository;
import ru.otus.hw.school.services.StudentService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final HomeworkRepository homeworkRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> getAll(Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll(pageable);
        List<StudentDto> studentDtos = studentsPage.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();

        return new PageImpl<>(studentDtos, studentsPage.getPageable(), studentsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDto> getGroupsByStudentId(UUID studentId) {
        return getById(studentId)
                .getGroups();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getHomeworksByStudentId(UUID studentId) {
        List<Homework> homeworks = homeworkRepository.findAllByStudentId(studentId);

        return homeworks.stream()
                .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getById(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент с id %s не найден".formatted(id)));

        StudentDto studentDto = modelMapper.map(student, StudentDto.class);

        List<GroupDto> groups = Optional.ofNullable(student.getGroups()).orElse(List.of())
                .stream()
                .map(group -> new GroupDto()
                        .setId(group.getId())
                        .setGroupName(group.getGroupName())
                )
                .toList();

        studentDto.setGroups(groups);

        return studentDto;
    }

    @Override
    @Transactional
    public StudentDto create(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        student.setCreatedAt(Instant.now());
        student = studentRepository.save(student);

        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    @Transactional
    public StudentDto update(StudentDto studentDto) {
        UUID id = studentDto.getId();
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент с id %s не найден".formatted(id)));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setUpdatedAt(Instant.now());

        List<GroupDto> groupsDtos = studentDto.getGroups();

        if (CollectionUtils.isNotEmpty(groupsDtos)) {
            List<UUID> groupIds = groupsDtos.stream().map(GroupDto::getId).toList();
            List<Group> groups = groupRepository.findAllByIdIn(groupIds);
            student.setGroups(groups);
        } else {
            student.setGroups(null);
        }

        student = studentRepository.save(student);

        List<GroupDto> groups = Optional.ofNullable(student.getGroups()).orElse(List.of()).stream()
                .map(group -> new GroupDto()
                        .setId(group.getId())
                        .setGroupName(group.getGroupName())
                        .setStartDate(group.getStartDate())
                        .setEndDate(group.getEndDate())
                        .setStatus(group.getStatus())
                ).toList();

        return modelMapper.map(student, StudentDto.class)
                .setGroups(groups);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }
}
