package ru.otus.hw15.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class SoftwareEngineerCareerGatewayTest {

    @Autowired
    private SoftwareEngineerCareerGateway softwareEngineerCareerGateway;

    @Test
    void shouldReturnSoftwareEngineers() {
        Collection<Trainee> trainees = generateTrainees();
        Collection<SeniorEngineer> seniorEngineers = softwareEngineerCareerGateway.process(trainees);

        assertFalse(seniorEngineers.isEmpty());
        assertEquals(trainees.size(), seniorEngineers.size());
    }

    private static Collection<Trainee> generateTrainees() {
        List<Trainee> trainees = new ArrayList<>();

        List<String> names = List.of(
                "Алексей Викторов",
                "Виктор Сергеев"
        );

        for (String name : names) {
            Trainee trainee = new Trainee();
            trainee.setName(name);
            trainee.setGraduated(true);
            trainee.setAge(new Random().nextInt(4) + 20);

            trainees.add(trainee);
        }

        return trainees;
    }
}