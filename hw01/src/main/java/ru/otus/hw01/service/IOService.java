package ru.otus.hw01.service;

public interface IOService {
    void print(String s);

    void printLine(String s);

    void printFormattedLine(String s, Object ...args);
}
