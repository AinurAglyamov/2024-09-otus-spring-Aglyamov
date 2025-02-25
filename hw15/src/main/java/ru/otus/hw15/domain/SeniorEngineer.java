package ru.otus.hw15.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class SeniorEngineer {
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
     * Список скиллов
     */
    private Set<String> skills;

    /**
     * Размер заработный платы
     */
    private BigDecimal salary;

    /**
     * Опыт работы (количество лет)
     */
    private Integer experienceYears;

    /**
     * Опыт управления/менторства (количество лет)
     */
    private Integer mentoringYears;
}
