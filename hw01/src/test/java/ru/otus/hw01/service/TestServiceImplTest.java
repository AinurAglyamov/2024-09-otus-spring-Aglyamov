package ru.otus.hw01.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw01.dao.QuestionDao;
import ru.otus.hw01.domain.Answer;
import ru.otus.hw01.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void textExecuteTest() {
        var text = "Do you like Java and Spring?";
        List<Answer> answers = new ArrayList<>();

        var answer1 = new Answer("yes", false);
        answers.add(answer1);
        var answer2 = new Answer("no", true);
        answers.add(answer2);

        var question = new Question(text, answers);

        Mockito.when(questionDao.findAll()).thenReturn(List.of(question));
        testService.executeTest();

        verify(ioService).printLine(text);
        verify(ioService).printLine(answer1.text());
        verify(ioService).printLine(answer2.text());
    }
}