package ru.otus.hw15.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.JuniorEngineer;
import ru.otus.hw15.domain.MiddleEngineer;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;
import ru.otus.hw15.service.CompanyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Override
    public JuniorEngineer hireTrainee(Trainee trainee) {
        log.info("Нанимаем стажера {} и делаем его Джуном!", trainee.getName());
        var juniorEngineer = new JuniorEngineer();

        juniorEngineer.setName(trainee.getName());
        juniorEngineer.setAge(trainee.getAge());
        juniorEngineer.setSkills(trainee.getSkills());
        juniorEngineer.setGraduated(trainee.isGraduated());
        juniorEngineer.setSalary(BigDecimal.valueOf(50000L));

        return juniorEngineer;
    }

    @Override
    public MiddleEngineer promoteToMiddle(JuniorEngineer juniorEngineer) {
        log.info("Повышаем до Миддла джуна: {}!", juniorEngineer.getName());

        var middleEngineer = new MiddleEngineer();
        middleEngineer.setName(juniorEngineer.getName());
        middleEngineer.setAge(juniorEngineer.getAge() + 1);
        Set<String> skills = juniorEngineer.getSkills();
        skills.add("Maven");
        skills.add("Gradle");
        skills.add("Postgres");
        middleEngineer.setSkills(skills);
        middleEngineer.setExperienceYears(1);

        //увеличение зарплаты максимум в 3 раза
        double multiply = (Math.random() * (3)) + 1;

        BigDecimal newSalary = juniorEngineer.getSalary().multiply(BigDecimal.valueOf(multiply))
                .setScale(2, RoundingMode.HALF_UP);;

        middleEngineer.setSalary(newSalary);

        log.info("Джун {} повышен до Миддла, новая зарплата: {} рублей!!", juniorEngineer.getName(), newSalary);

        return middleEngineer;
    }

    @Override
    public SeniorEngineer promoteToSenior(MiddleEngineer middleEngineer) {
        log.info("Повышаем до Сеньора мидла: {}!", middleEngineer.getName());

        var seniorEngineer = new SeniorEngineer();
        seniorEngineer.setName(middleEngineer.getName());
        seniorEngineer.setAge(middleEngineer.getAge() + 3);

        Set<String> skills = middleEngineer.getSkills();
        skills.addAll(Set.of("Kafka", "Spring Webflux", "Security", "Optimizing SQL Queries"));

        seniorEngineer.setSkills(skills);
        seniorEngineer.setExperienceYears(4);

        //увеличение зарплаты максимум в 2 раза
        double multiply = (Math.random() * (2)) + 1;

        BigDecimal newSalary = middleEngineer.getSalary().multiply(BigDecimal.valueOf(multiply))
                .setScale(2, RoundingMode.HALF_UP);
        seniorEngineer.setSalary(newSalary);

        log.info("Мидл {} повышен до Сеньора, новая зарплата: {} рублей!!", middleEngineer.getName(), newSalary);

        return seniorEngineer;
    }
}
