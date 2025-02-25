package ru.otus.hw04.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw04.domain.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPromptLocalized("StudentService.input.first.name");
        var lastName = ioService.readStringWithPromptLocalized("StudentService.input.last.name");
        return new Student(firstName, lastName);
    }
}
