package ru.otus.hw15.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw15.service.CareerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
	private final CareerService careerService;

	@Override
	public void run(String... args) {
		careerService.fromTraineeToSeniorEngineer();
	}
}
