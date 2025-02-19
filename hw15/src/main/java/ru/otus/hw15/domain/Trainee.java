package ru.otus.hw15.domain;

import lombok.Data;

import java.util.Set;

@Data
public class Trainee {
    /**
     * Имя
     */
    private String name;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Признак наличия диплома о высшем образовании
     */
    private boolean graduated;

    /**
     * Полученные баллы за обучение
     */
    private int points;

    /**
     * Навыки
     */
    private Set<String> skills;

}
