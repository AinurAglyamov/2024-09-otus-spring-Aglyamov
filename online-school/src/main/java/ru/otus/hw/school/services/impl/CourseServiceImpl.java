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
import ru.otus.hw.school.dto.GroupDto;
import ru.otus.hw.school.dto.TeacherDto;
import ru.otus.hw.school.models.Course;
import ru.otus.hw.school.models.Group;
import ru.otus.hw.school.repositories.CourseRepository;
import ru.otus.hw.school.repositories.TeacherRepository;
import ru.otus.hw.school.services.CourseService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDto> getAll(Pageable pageable) {
        Page<Course> coursesPage = courseRepository.findAll(pageable);
        List<CourseDto> courseDtos = coursesPage.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .toList();

        return new PageImpl<>(courseDtos, coursesPage.getPageable(), coursesPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс с id %s не найден".formatted(id)));

        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDto> getGroupsByCourseId(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Курс с id %s не найден".formatted(courseId)));

        List<Group> groups = Optional.ofNullable(course.getGroups()).orElse(List.of());

        return groups.stream()
                .map(group -> new GroupDto()
                        .setId(group.getId())
                        .setGroupName(group.getGroupName())
                        .setStartDate(group.getStartDate())
                        .setEndDate(group.getEndDate())
                ).toList();
    }

    @Override
    @Transactional
    public CourseDto create(CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        course.setCreatedAt(Instant.now());

        course = courseRepository.save(course);

        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    @Transactional
    public CourseDto update(CourseDto courseDto) {
        var id = courseDto.getId();
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс с id %s не найден".formatted(id)));
        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());
        course.setUpdatedAt(Instant.now());

        var teachersDtos = courseDto.getTeachers();

        if (CollectionUtils.isNotEmpty(teachersDtos)) {
            List<UUID> ids = teachersDtos.stream().map(TeacherDto::getId).toList();
            course.setTeachers(teacherRepository.findAllByIdIn(ids));
        } else {
            course.setTeachers(null);
        }

        Course savedCourse = courseRepository.save(course);

        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public void deleteById(UUID id) {
        courseRepository.deleteById(id);
    }
}
