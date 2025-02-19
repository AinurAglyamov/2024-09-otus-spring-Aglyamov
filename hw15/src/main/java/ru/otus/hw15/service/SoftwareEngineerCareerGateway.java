package ru.otus.hw15.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw15.domain.SeniorEngineer;
import ru.otus.hw15.domain.Trainee;

import java.util.Collection;

@MessagingGateway
public interface SoftwareEngineerCareerGateway {

    @Gateway(requestChannel = "traineesChannel", replyChannel = "seniorsChannel")
    Collection<SeniorEngineer> process(Collection<Trainee> trainees);
}
