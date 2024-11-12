package ru.otus.hw04.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw04.service.TestRunnerService;

@ShellComponent(value = "Testing Commands")
@RequiredArgsConstructor
public class TestingCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start test command", key = {"test", "t"})
    public void test() {
        testRunnerService.run();
    }
}
