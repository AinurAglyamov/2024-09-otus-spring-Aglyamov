package ru.otus.hw15.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;
import ru.otus.hw15.service.CareerService;
import ru.otus.hw15.service.SoftwareEngineerCareerGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CareerServiceImpl implements CareerService {

    private final SoftwareEngineerCareerGateway softwareEngineerCareerGateway;

    @Override
    public void fromTraineeToSeniorEngineer() {
        Collection<Trainee> items = generateTrainees();
        log.info("Пачка стажеров: {}",
                items.stream().map(Trainee::getName)
                        .collect(Collectors.joining(",")));

        Collection<SeniorEngineer> seniorEngineers = softwareEngineerCareerGateway.process(items);

        log.info("Стали сеньорами: {}", seniorEngineers.stream()
                .map(SeniorEngineer::getName)
                .collect(Collectors.joining(",")));
    }

    private static Collection<Trainee> generateTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        List<String> names = List.of(
                "Александр Антонов",
                "Антон Антонов",
                "Антон Михайлов",
                "Михаил Валериев",
                "Валерий Леонтьев",
                "Леонтий Олегов",
                "Олег Айнуров",
                "Айнур Ильнуров",
                "Ильнур Александров"
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
