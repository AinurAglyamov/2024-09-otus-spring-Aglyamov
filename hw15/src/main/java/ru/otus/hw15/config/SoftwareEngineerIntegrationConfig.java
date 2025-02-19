package ru.otus.hw15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw15.service.CompanyService;
import ru.otus.hw15.service.TrainingCenter;

import java.util.Objects;

@Configuration
public class SoftwareEngineerIntegrationConfig {

	@Bean
	public MessageChannelSpec<?, ?> traineesChannel() {
		return MessageChannels.direct();
	}

	@Bean
	public MessageChannelSpec<?, ?> seniorsChannel() {
		return MessageChannels.queue(100);
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerSpec poller() {
		return Pollers.fixedRate(100).maxMessagesPerPoll(2);
	}

	@Bean
	public IntegrationFlow softwareEngineerFlow(TrainingCenter trainingCenter, CompanyService companyService) {
		return IntegrationFlow.from(traineesChannel())
				.split()
				.handle(trainingCenter, "teachTrainee")
				.handle(companyService, "hireTrainee")
				.filter(Objects::nonNull)
				.handle(companyService, "promoteToMiddle")
				.transform(companyService, "promoteToSenior")
				.aggregate()
				.channel(seniorsChannel())
				.get();
	}
}
