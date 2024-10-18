package ru.otus.hw01.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw01.dao.QuestionDao;
import ru.otus.hw01.domain.Answer;
import ru.otus.hw01.domain.Question;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();

        questions.forEach(question -> {
            ioService.printLine(question.text());
            printAnswers(question.answers());
            ioService.printLine("");
        });
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
