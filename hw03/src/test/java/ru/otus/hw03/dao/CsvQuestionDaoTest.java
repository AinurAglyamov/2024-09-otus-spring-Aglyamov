package ru.otus.hw03.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw03.config.TestFileNameProvider;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider testFileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @Test
    void testFindAll() {
        when(testFileNameProvider.getTestFileName()).thenReturn("questions-test.csv");
        List<Question> questions = csvQuestionDao.findAll();
        Question question = questions.get(0);
        List<Answer> answers = question.answers();
        Answer correctAnswer = answers.get(0);
        Answer incorrectAnswer = answers.get(1);

        assertEquals(4, questions.size());
        assertEquals(3, answers.size());
        assertTrue(correctAnswer.isCorrect());
        assertFalse(incorrectAnswer.isCorrect());
    }
}