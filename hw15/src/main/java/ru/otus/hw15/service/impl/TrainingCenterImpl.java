package ru.otus.hw15.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.Trainee;
import ru.otus.hw15.service.TrainingCenter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
public class TrainingCenterImpl implements TrainingCenter {

    @Override
    public Trainee teachTrainee(Trainee trainee) {
        log.info("Жестко обучаем стажера {}", trainee.getName());

        int points = new Random().nextInt(10) + 1;
        trainee.setPoints(points);

        if (points == 1) {
            log.warn("Стажер {} необучаемый", trainee.getName());
            trainee.setSkills(new HashSet<>(Set.of("Java Basics")));
        } else if (points <= 5) {
            trainee.setSkills(new HashSet<>(Set.of("Java Basics, DB Basics")));
        } else {
            trainee.setSkills(new HashSet<>(Set.of("Java Basics, DB Basics, Spring, Hibernate, Git, Algorithms")));
        }

        return trainee;
    }
}
