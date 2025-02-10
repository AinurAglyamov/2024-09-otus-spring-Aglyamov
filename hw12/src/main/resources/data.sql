insert into authors(full_name)
values ('Джоан Роулинг'), ('Федор Достоевский'), ('Артур Конан Дойл');

insert into genres(name)
values ('Фэнтези'), ('Роман'), ('Детектив');

insert into books(title, author_id, genre_id)
values ('Гарри Повар и Кубок борща', 1, 1), ('Идиот', 2, 2), ('Этюд в багровых тонах', 3, 3);

insert into comments(text, comment_date, book_id)
values ('Чет не очень', '2024-10-10'::date, 1),
       ('Хорошая пародия на Гарри Поттера', '2024-10-11'::date, 1),
       ('Классика', '2024-10-12'::date, 2),
       ('Советую', '2024-10-13'::date, 2),
       ('Очень увлекательно', '2024-10-14'::date, 3),
       ('Шедевр', '2024-10-15'::date, 3);

insert into users(login, password)
values
    ('valera11', '$2a$12$jrfjaA7HSkKniO7X9/RUIu2jrCcVpXSq2YMjpthIy.IEoSifrbhLe'),--pwd=123
    ('uasya22', '$2a$12$i9LWKjpZYrqqGl4b.h8ooetd3XFaJVG7yb5Lt1AJIZat1O7eobqDi');--pwd=password
