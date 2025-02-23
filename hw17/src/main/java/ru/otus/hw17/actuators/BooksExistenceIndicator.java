package ru.otus.hw17.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw17.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class BooksExistenceIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        if (bookRepository.count() > 0) {
            return Health.up().withDetail("message", "Ура, господа, книги у нас есть!").build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Книги закончились, скоро привезут!")
                    .build();
        }
    }
}
