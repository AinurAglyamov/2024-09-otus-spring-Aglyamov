package ru.otus.hw01.config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppProperties implements TestFileNameProvider {
    private final String testFileName;

    @Override
    public String getTestFileName() {
        return testFileName;
    }
}
