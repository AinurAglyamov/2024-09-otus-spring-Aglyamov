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
import ru.otus.hw.school.dto.CourseDto;
import ru.otus.hw.school.dto.HomeworkDto;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Course;
import ru.otus.hw.school.models.Homework;
import ru.otus.hw.school.models.Teacher;
import ru.otus.hw.school.repositories.CourseRepository;
import ru.otus.hw.school.repositories.HomeworkRepository;
import ru.otus.hw.school.repositories.TeacherRepository;
import ru.otus.hw.school.services.TeacherService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final HomeworkRepository homeworkRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDto> getAll(Pageable pageable) {
        Page<Teacher> teachersPage = teacherRepository.findAll(pageable);
        List<TeacherDto> teacherDtos = teachersPage.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherDto.class))
                .toList();

        return new PageImpl<>(teacherDtos, teachersPage.getPageable(), teachersPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto getById(UUID id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Преподаватель с id %s не найден".formatted(id)));

        TeacherDto teacherDto = modelMapper.map(teacher, TeacherDto.class);

        List<CourseDto> courses = Optional.ofNullable(teacher.getCourses()).orElse(List.of())
                .stream()
                .map(course -> new CourseDto()
                        .setCourseName(course.getCourseName())
                        .setDescription(course.getDescription())
                ).toList();

        teacherDto.setCourses(courses);

        return teacherDto;
    }

    @Override
    @Transactional
    public TeacherDto create(TeacherDto teacherDto) {
        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        Teacher savedTeacher = teacherRepository.save(teacher);

        return modelMapper.map(savedTeacher, TeacherDto.class);
    }

    @Override
    @Transactional
    public TeacherDto update(TeacherDto teacherDto) {
        UUID id = teacherDto.getId();
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Преподаватель с id %s не найден".formatted(id)));
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setCountry(teacherDto.getCountry());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());

        List<CourseDto> coursesDtos = teacherDto.getCourses();

        if (CollectionUtils.isNotEmpty(coursesDtos)) {
            List<UUID> coursesIds = coursesDtos.stream().map(CourseDto::getId).toList();
            List<Course> courses = courseRepository.findAllByIdIn(coursesIds);
            teacher.setCourses(courses);
        } else {
            teacher.setCourses(null);
        }

        List<CourseDto> courses = Optional.ofNullable(teacher.getCourses()).orElse(List.of())
                .stream()
                .map(course -> new CourseDto()
                        .setCourseName(course.getCourseName())
                        .setDescription(course.getDescription())
                ).toList();

        teacher = teacherRepository.save(teacher);

        return modelMapper.map(teacher, TeacherDto.class)
                .setCourses(courses);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getHomeworksByTeacherId(UUID teacherId) {
        List<Homework> homeworks = homeworkRepository.findAllByTeacherId(teacherId);

        return homeworks.stream()
                .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                .toList();
    }
}
