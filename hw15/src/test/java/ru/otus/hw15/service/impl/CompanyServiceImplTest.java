package ru.otus.hw15.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw15.domain.JuniorEngineer;
import ru.otus.hw15.domain.MiddleEngineer;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;
import ru.otus.hw15.service.CompanyService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    private CompanyService companyService = new CompanyServiceImpl();

    @Test
    void shouldHireTraineeAndMakeAJuniorDeveloper() {
        var trainee = new Trainee();
        trainee.setName("Test");
        trainee.setGraduated(true);
        trainee.setAge(new Random().nextInt(4) + 20);
        trainee.setSkills(new HashSet<>(Set.of("Java Basics, DB Basics, Spring, Hibernate, Git, Algorithms")));
        trainee.setPoints(6);

        JuniorEngineer juniorEngineer = companyService.hireTrainee(trainee);

        assertNotNull(juniorEngineer);
        assertTrue(juniorEngineer.getSalary().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldPromoteToMiddleAndIncreaseSalary() {
        JuniorEngineer juniorEngineer = new JuniorEngineer();

        juniorEngineer.setName("Test");
        juniorEngineer.setGraduated(true);
        juniorEngineer.setAge(new Random().nextInt(4) + 20);
        juniorEngineer.setSkills(new HashSet<>(Set.of("Java Basics, DB Basics, Spring, Hibernate, Git, Algorithms")));
        juniorEngineer.setSalary(BigDecimal.valueOf(50000));

        MiddleEngineer middleEngineer = companyService.promoteToMiddle(juniorEngineer);

        assertNotNull(middleEngineer);
        assertTrue(middleEngineer.getSalary().compareTo(juniorEngineer.getSalary()) > 0);
    }

    @Test
    void shouldPromoteToSeniorAndIncreaseSalary() {
        MiddleEngineer middleEngineer = new MiddleEngineer();

        middleEngineer.setName("Test");
        middleEngineer.setAge(new Random().nextInt(4) + 20);
        middleEngineer.setSkills(new HashSet<>(Set.of("Java Basics, DB Basics, Spring, Hibernate, Git, Algorithms")));
        middleEngineer.setSalary(BigDecimal.valueOf(150000));

        SeniorEngineer seniorEngineer = companyService.promoteToSenior(middleEngineer);

        assertNotNull(seniorEngineer);
        assertTrue(seniorEngineer.getSalary().compareTo(middleEngineer.getSalary()) > 0);
    }
}