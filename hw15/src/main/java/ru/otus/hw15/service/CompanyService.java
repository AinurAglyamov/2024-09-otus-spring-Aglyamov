package ru.otus.hw15.service;

import ru.otus.hw15.domain.JuniorEngineer;
import ru.otus.hw15.domain.MiddleEngineer;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;

public interface CompanyService {

    /**
     * Найм стажера в компанию
     * @param trainee стажер
     * @return junior-специалист
     */
    JuniorEngineer hireTrainee(Trainee trainee);

    /**
     * Повышение до уровня Middle-инженер
     * @param juniorEngineer начинающий инженер
     * @return Middle-инженер
     */
    MiddleEngineer promoteToMiddle(JuniorEngineer juniorEngineer);

    /**
     * Повышение до уровня Senior-инженер
     * @param middleEngineer мидл-инженер
     * @return Senior-инженер
     */
    SeniorEngineer promoteToSenior(MiddleEngineer middleEngineer);
}
