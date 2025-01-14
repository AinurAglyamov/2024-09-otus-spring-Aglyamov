insert into authors(full_name)
values ('Джоан Роулинг'), ('Федор Достоевский'), ('Артур Конан Дойл');

insert into genres(name)
values ('Фэнтези'), ('Роман'), ('Детектив');

insert into books(title, author_id, genre_id)
values ('Гарри Повар и Кубок борща', 1, 1), ('Идиот', 2, 2), ('Этюд в багровых тонах', 3, 3);
