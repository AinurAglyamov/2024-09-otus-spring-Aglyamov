package ru.otus.hw02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw02.dao.CsvQuestionDao;

@PropertySource("classpath:application-test.properties")
public class TestAppConfig {

    @Bean
    public TestFileNameProvider testFileNameProvider() {
        return new AppProperties();
    }

    @Bean
    public CsvQuestionDao csvQuestionDao(TestFileNameProvider testFileNameProvider) {
        return new CsvQuestionDao(testFileNameProvider);
    }

}
