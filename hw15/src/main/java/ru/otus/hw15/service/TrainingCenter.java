package ru.otus.hw15.service;

import ru.otus.hw15.domain.Trainee;

public interface TrainingCenter {

    /**
     * Обучение стажера
     * @param trainee стажер
     * @return обученный стажер
     */
    Trainee teachTrainee(Trainee trainee);
}
