package ru.otus.hw11.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.models.Book;

public interface BookRepositoryCustom {

    /**
     * Найти книгу по идентификатору книги
     * @param id идентификатор книги
     * @return книга
     */
    Mono<Book> findById(Long id);

    /**
     * Найти все книги
     * @return список книг
     */
    Flux<Book> findAll();

    /**
     * Сохранить книгу
     * @param book книга
     * @return сохраненная книга
     */
    Mono<Book> save(Book book);

    /**
     * Удалить книгу по идентификатору
     * @param id идентификатор книги
     */
    Mono<Void> deleteById(long id);
}
