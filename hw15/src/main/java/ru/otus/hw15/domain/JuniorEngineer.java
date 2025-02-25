package ru.otus.hw15.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Junior-инженер
 */
@Data
public class JuniorEngineer {
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
}
