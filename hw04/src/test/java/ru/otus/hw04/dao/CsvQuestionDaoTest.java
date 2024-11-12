package ru.otus.hw04.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw04.config.TestFileNameProvider;
import ru.otus.hw04.domain.Answer;
import ru.otus.hw04.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {

    @MockBean
    private TestFileNameProvider testFileNameProvider;

    @Autowired
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