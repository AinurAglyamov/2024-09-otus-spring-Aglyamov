package ru.otus.hw02.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw02.config.TestAppConfig;
import ru.otus.hw02.domain.Answer;
import ru.otus.hw02.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
class CsvQuestionDaoTest {

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    void testFindAll() {
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