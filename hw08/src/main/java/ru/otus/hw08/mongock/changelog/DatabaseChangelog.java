package ru.otus.hw08.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.DBRef;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "aynur.aglyamov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initData", author = "aynur.aglyamov")
    public void initData(MongoDatabase db) {
        MongoCollection<Document> authorsCollection = db.getCollection("authors");

        var jroling = new Document().append("_id", "1").append("fullName", "Джоан Роулинг");
        var dostoevskiy = new Document().append("_id", "2").append("fullName", "Фёдор Достоевский");
        var conanDoyl = new Document().append("_id", "3").append("fullName", "Артур Конан Дойл");

        MongoCollection<Document> genresCollection = db.getCollection("genres");
        var fantasy = new Document().append("_id", "1").append("name", "Фэнтези");
        var novel = new Document().append("_id", "2").append("name", "Роман");
        var detective = new Document().append("_id", "3").append("name", "Детектив");

        MongoCollection<Document> commentsCollection = db.getCollection("comments");

        var harryPotComment1 = new Document().append("_id", "1").append("text", "Чет не очень");
        var harryPotComment2 = new Document().append("_id", "2").append("text", "Хорошая пародия на Гарри Поттера");
        var idiotComment1 = new Document().append("_id", "3").append("text", "Классика");
        var idiotComment2 = new Document().append("_id", "4").append("text", "Советую");
        var etudComment1 = new Document().append("_id", "5").append("text", "Очень увлекательно");
        var etudComment2 = new Document().append("_id", "6").append("text", "Шедевр");

        MongoCollection<Document> booksCollection = db.getCollection("books");

        var harryPot = new Document().append("_id", "1")
                .append("title", "Гарри Повар и Кубок борща")
                .append("author", new DBRef("authors", jroling.get("_id")))
                .append("genre", new DBRef("genres", fantasy.get("_id")))
                .append("comments", List.of(
                        new DBRef("comments", harryPotComment1.get("_id")),
                        new DBRef("comments", harryPotComment2.get("_id")))
                );

        var idiot = new Document().append("_id", "2")
                .append("title", "Идиот")
                .append("author", new DBRef("authors", dostoevskiy.get("_id")))
                .append("genre", new DBRef("genres", novel.get("_id")))
                .append("comments",
                        List.of(new DBRef("comments", idiotComment1.get("_id")),
                                new DBRef("comments", idiotComment2.get("_id")))
                );

        var etud = new Document().append("_id", "3")
                .append("title", "Этюд в багровых тонах")
                .append("author", new DBRef("authors", conanDoyl.get("_id")))
                .append("genre", new DBRef("genres", detective.get("_id")))
                .append("genre", new DBRef("genres", novel.get("_id")))
                .append("comments",
                        List.of(new DBRef("comments", etudComment1.get("_id")),
                                new DBRef("comments", etudComment2.get("_id"))
                        )
                );

        jroling.append("books", List.of(new DBRef("books", harryPot.get("_id"))));
        dostoevskiy.append("books", List.of(new DBRef("books", idiot.get("_id"))));
        conanDoyl.append("books", List.of(new DBRef("books", etud.get("_id"))));

        authorsCollection.insertMany(List.of(jroling, dostoevskiy, conanDoyl));
        genresCollection.insertMany(List.of(fantasy, novel, detective));
        booksCollection.insertMany(List.of(harryPot, idiot, etud));
        commentsCollection.insertMany(
                List.of(harryPotComment1, harryPotComment2, idiotComment1, idiotComment2, etudComment1, etudComment2)
        );

    }
}
