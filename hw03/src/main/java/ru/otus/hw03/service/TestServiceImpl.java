package ru.otus.hw03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw03.dao.QuestionDao;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Student;
import ru.otus.hw03.domain.TestResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printLine(question.text());
            List<Answer> answers = question.answers();
            printAnswers(answers);
            int userAnswer = ioService.readIntForRangeWithPromptLocalized(
                    1,
                    answers.size(),
                    "TestService.prompt",
                    "TestService.error"
            );
            ioService.printLine("");

            var isAnswerValid = answers.get(userAnswer - 1).isCorrect();

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void printAnswers(List<Answer> answers) {
        var optionCounter = new AtomicInteger(1);
        answers.forEach(answer -> {
            String optionPrefix = "\t" + optionCounter.getAndIncrement() + ") ";
            ioService.print(optionPrefix);
            ioService.printLine(answer.text());
        });
    }
}
