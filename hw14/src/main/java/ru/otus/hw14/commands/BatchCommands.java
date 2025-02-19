package ru.otus.hw14.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Properties;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private final JobOperator jobOperator;

    @ShellMethod(value = "migrate books", key = "mb")
    public void migrateBooksCommands() throws Exception {
        Properties properties = new Properties();

        Long executionId = jobOperator.start(IMPORT_BOOK_JOB_NAME, properties);
        System.out.println(jobOperator.getSummary(executionId));
    }
}
