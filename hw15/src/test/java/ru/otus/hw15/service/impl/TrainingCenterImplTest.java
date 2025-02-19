package ru.otus.hw15.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw15.domain.Trainee;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TrainingCenterImplTest {

    private TrainingCenterImpl trainingCenter = new TrainingCenterImpl();

    @Test
    void shouldTeachTrainee() {
        Trainee trainee = new Trainee();
        trainee.setName("Test");
        trainee.setGraduated(true);
        trainee.setAge(new Random().nextInt(4) + 20);

        Trainee learnedTrainee = trainingCenter.teachTrainee(trainee);

        assertTrue(learnedTrainee.getPoints() > 0);
    }
}