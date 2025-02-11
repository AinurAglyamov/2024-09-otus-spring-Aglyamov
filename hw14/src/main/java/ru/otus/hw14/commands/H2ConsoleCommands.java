package ru.otus.hw14.commands;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class H2ConsoleCommands {

    @ShellMethod(value = "Start h2 console", key = "cs")
    public void runH2Console() throws Exception {
        Console.main();
    }
}
