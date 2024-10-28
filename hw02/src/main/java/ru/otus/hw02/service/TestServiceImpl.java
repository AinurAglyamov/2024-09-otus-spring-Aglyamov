package ru.otus.hw02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.Answer;
import ru.otus.hw02.domain.Student;
import ru.otus.hw02.domain.TestResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printLine(question.text());
            List<Answer> answers = question.answers();
            printAnswers(answers);
            int userAnswer = ioService.readIntForRangeWithPrompt(
                    1,
                    answers.size(),
                    "Your answer:",
                    "Selected option is out of range. Please enter again:"
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
