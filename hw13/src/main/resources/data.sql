insert into authors(full_name)
values ('Джоан Роулинг'), ('Федор Достоевский'), ('Артур Конан Дойл');

insert into genres(name)
values ('Фэнтези'), ('Роман'), ('Детектив');

insert into books(title, author_id, genre_id)
values ('Гарри Повар и Кубок борща', 1, 1),
       ('Идиот', 2, 2),
       ('Этюд в багровых тонах', 3, 3);

insert into comments(text, comment_date, book_id)
values ('Чет не очень', '2024-10-10'::date, 1),
       ('Хорошая пародия на Гарри Поттера', '2024-10-11'::date, 1),
       ('Классика', '2024-10-12'::date, 2),
       ('Советую', '2024-10-13'::date, 2),
       ('Очень увлекательно', '2024-10-14'::date, 3),
       ('Шедевр', '2024-10-15'::date, 3);

insert into users(login, password, role)
values
    ('alesha', '$2a$12$jrfjaA7HSkKniO7X9/RUIu2jrCcVpXSq2YMjpthIy.IEoSifrbhLe', 'USER'),--pwd=123
    ('max', '$2a$12$i9LWKjpZYrqqGl4b.h8ooetd3XFaJVG7yb5Lt1AJIZat1O7eobqDi', 'USER'),--pwd=password
    ('super', '$2a$12$57ZU7mHByuUvWljBz6naVuRO7rMupMmkWYtTxC4ArfSgTyIy/VwBC', 'SUPER');--pwd=321

INSERT INTO acl_sid (principal, sid) VALUES
(1, 'alesha'),
(1, 'seryozha'),
(0, 'ROLE_SUPER');

INSERT INTO acl_class (class) VALUES ('ru.otus.hw13.dto.BookDto');

insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values
(1, 1, NULL, 3, 0),
(1, 2, NULL, 3, 0),
(1, 3, NULL, 3, 0);

insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values
(1, 1, 1, 1, 1, 1, 1),
(1, 2, 1, 2, 1, 1, 1),
(1, 3, 3, 1, 1, 1, 1),
(1, 4, 3, 2, 1, 1, 1),
(2, 1, 2, 1, 1, 1, 1),
(2, 2, 2, 2, 1, 1, 1),
(2, 3, 3, 1, 1, 1, 1),
(2, 4, 3, 2, 1, 1, 1),
(3, 1, 3, 1, 1, 1, 1),
(3, 2, 3, 2, 1, 1, 1);