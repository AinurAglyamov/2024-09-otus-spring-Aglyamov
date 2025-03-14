package ru.otus.hw02.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.Answer;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.domain.Student;
import ru.otus.hw02.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void executeTestFor() {
        var student = new Student("sidney", "crosby");
        var text = "Do you like Java and Spring?";
        List<Answer> answers = new ArrayList<>();

        var answer1 = new Answer("yes", false);
        answers.add(answer1);
        var answer2 = new Answer("no", true);
        answers.add(answer2);

        var question = new Question(text, answers);

        when(questionDao.findAll()).thenReturn(List.of(question));
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(2);
        TestResult testResult = testService.executeTestFor(student);

        verify(ioService).printLine(text);
        verify(ioService).printLine(answer1.text());
        verify(ioService).printLine(answer2.text());

        assertEquals(1, testResult.getRightAnswersCount());
    }
}