package ru.otus.hw14.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw14.converters.AuthorConverter;
import ru.otus.hw14.converters.BookConverter;
import ru.otus.hw14.converters.GenreConverter;
import ru.otus.hw14.models.mongo.MongoAuthor;
import ru.otus.hw14.models.mongo.MongoBook;
import ru.otus.hw14.models.mongo.MongoGenre;
import ru.otus.hw14.models.relational.Author;
import ru.otus.hw14.models.relational.Book;
import ru.otus.hw14.models.relational.Genre;
import ru.otus.hw14.repositories.AuthorRepository;
import ru.otus.hw14.repositories.GenreRepository;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobConfig {
    private static final int CHUNK_SIZE = 5;

    private static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final BookConverter bookConverter;

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    @Bean
    public Job importBooksJob(Step transformAuthorsStep, Step transformGenresStep, Step transformBooksStep) {
        return new JobBuilder(IMPORT_BOOK_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public MongoPagingItemReader<MongoAuthor> authorReader(MongoTemplate mongoTemplate) {
        Map<String, Direction> sortMap = Map.of("id", Direction.DESC);

        return new MongoPagingItemReaderBuilder<MongoAuthor>()
                .name("AuthorItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(MongoAuthor.class)
                .pageSize(10)
                .sorts(sortMap)
                .build();
    }

    @Bean
    public MongoPagingItemReader<MongoGenre> genreReader(MongoTemplate mongoTemplate) {
        Map<String, Direction> sortMap = Map.of("id", Direction.DESC);

        return new MongoPagingItemReaderBuilder<MongoGenre>()
                .name("GenreItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(MongoGenre.class)
                .pageSize(10)
                .sorts(sortMap)
                .build();
    }

    @Bean
    public MongoPagingItemReader<MongoBook> bookReader(MongoTemplate mongoTemplate) {
        Map<String, Direction> sortMap = Map.of("id", Direction.DESC);

        return new MongoPagingItemReaderBuilder<MongoBook>()
                .name("BookItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .pageSize(10)
                .sorts(sortMap)
                .build();
    }

    @Bean
    public ItemProcessor<MongoBook, Book> bookProcessor(BookConverter bookConverter) {
        return bookConverter::mongoBookToBook;
    }

    @Bean
    public ItemProcessor<MongoGenre, Genre> genreProcessor(GenreConverter genreConverter) {
        return genreConverter::mongoGenreToGenre;
    }

    @Bean
    public ItemProcessor<MongoAuthor, Author> authorProcessor(AuthorConverter authorConverter) {
        return authorConverter::mongoAuthorToRelationalAuthor;
    }

    @Bean
    public Step transformAuthorsStep(ItemReader<MongoAuthor> authorsReader,
                                     ItemWriter<Author> authorsWriter,
                                     ItemProcessor<MongoAuthor, Author> authorsItemProcessor) {
        Map<String, Direction> sortMap = Map.of("", Direction.DESC);

        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<MongoAuthor, Author>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(authorsReader)
                .processor(authorsItemProcessor)
                .writer(authorsWriter)
                .build();
    }

    @Bean
    public Step transformGenresStep(ItemReader<MongoGenre> genreReader, ItemWriter<Genre> genreWriter,
                                    ItemProcessor<MongoGenre, Genre> genreItemProcessor) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<MongoGenre, Genre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(genreReader)
                .processor(genreItemProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<MongoBook> bookReader, ItemWriter<Book> bookWriter,
                                   ItemProcessor<MongoBook, Book> bookItemProcessor) {
        return new StepBuilder("transformBooksStep", jobRepository)
                .<MongoBook, Book>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookReader)
                .processor(bookItemProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public JpaItemWriter<Author> authorWriter(EntityManager entityManager) {
        return new JpaItemWriterBuilder<Author>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .build();
    }

    @Bean
    public JpaItemWriter<Genre> genreWriter(EntityManager entityManager) {
        return new JpaItemWriterBuilder<Genre>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .build();
    }

    @Bean
    public JpaItemWriter<Book> bookWriter(AuthorRepository authorRepository,
                                          GenreRepository genreRepository,
                                          EntityManager entityManager) {
        var bookItemWriter = new BookItemWriter(authorRepository, genreRepository);
        bookItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());

        return bookItemWriter;
    }

    @RequiredArgsConstructor
    public static class BookItemWriter extends JpaItemWriter<Book> {

        private final AuthorRepository authorRepository;

        private final GenreRepository genreRepository;

        @Override
        public void write(Chunk<? extends Book> books) {
            Set<BigInteger> mongoAuthorIds = new HashSet<>();
            Set<BigInteger> mongoGenreIds = new HashSet<>();

            books.forEach(book -> {
                mongoAuthorIds.add(book.getMongoAuthorId());
                mongoGenreIds.add(book.getMongoGenreId());
            });

            Map<BigInteger, Author> authorsByMongoId = authorRepository.findAllByMongoId(mongoAuthorIds).stream()
                    .collect(Collectors.toMap(Author::getMongoId, Function.identity()));

            Map<BigInteger, Genre> genresByMongoId = genreRepository.findAllByMongoIds(mongoGenreIds).stream()
                    .collect(Collectors.toMap(Genre::getMongoId, Function.identity()));

            books.forEach(b -> {
                b.setAuthor(authorsByMongoId.get(b.getMongoAuthorId()));
                b.setGenre(genresByMongoId.get(b.getMongoGenreId()));
            });

            super.write(books);
        }
    }
}
