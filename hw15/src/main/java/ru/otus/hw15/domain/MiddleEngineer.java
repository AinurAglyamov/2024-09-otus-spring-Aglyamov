package ru.otus.hw15.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class MiddleEngineer {
    /**
     * Имя
     */
    private String name;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Размер заработный платы
     */
    private BigDecimal salary;

    /**
     * Список скиллов
     */
    private Set<String> skills;

    /**
     * Опыт работы (количество лет)
     */
    private Integer experienceYears;
}
