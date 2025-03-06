package ru.otus.hw18.services;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import ru.otus.hw18.dto.BookDto;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class BookServiceRateLimiterTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName(" должен вернуть пустой список Книг если превышено количество запросов")
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void shouldReturnEmptyBooksListWhenNumberOfRequestsAreExceeded() throws InterruptedException {
        List<BookDto> books = bookService.findAll();
        assertThat(books).isNotEmpty();
        Thread.sleep(100);
        books = bookService.findAll();
        assertThat(books).isEmpty();
    }

    @TestConfiguration
    static class NestedConfiguration {
        @Bean
        public RateLimiterConfig rateLimiterConfig() {
            return RateLimiterConfig.custom()
                    .timeoutDuration(Duration.ofMillis(100))
                    .limitRefreshPeriod(Duration.ofSeconds(2))
                    .limitForPeriod(1)
                    .build();
        }

        @Bean
        public RateLimiter rateLimiter(RateLimiterConfig config) {
            return RateLimiter.of("defaultRateLimiter", config);
        }

        @Bean
        public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
            return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofSeconds(5))
                            .build())
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .build());
        }

        @Bean
        public CircuitBreaker circuitBreaker(CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
            return circuitBreakerFactory.create("defaultCircuitBreaker");
        }
    }
}