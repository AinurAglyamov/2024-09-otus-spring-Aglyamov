insert into courses (id, course_name, cost, description) values (uuid_generate_v4(), 'Java разработчик. Basic', 110000, 'Изучение основ Java');
insert into courses (id, course_name, cost, description) values (uuid_generate_v4(), 'Spring разработчик', 130000, 'Изучение Spring Framework');
insert into courses (id, course_name, cost, description) values (uuid_generate_v4(), 'Python разработчик. Advanced', 100000, 'Изучение продвинутого Python');
insert into courses (id, course_name, cost, description) values (uuid_generate_v4(), 'Golang разработчик. Basic', 160000, 'Изучение основ Golang');

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-1-java', '2025-01-09', '2025-06-09', 'ACTIVE', (select id from courses where course_name = 'Java разработчик. Basic'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-2-java', '2025-01-09', '2025-06-09', 'ACTIVE', (select id from courses where course_name = 'Java разработчик. Basic'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-1-spring', '2025-01-09', '2025-06-09', 'ACTIVE', (select id from courses where course_name = 'Spring разработчик'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-2-spring', '2025-02-09', '2025-07-09', 'ACTIVE', (select id from courses where course_name = 'Spring разработчик'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-1-python-adv', '2025-01-09', '2025-06-09', 'ACTIVE', (select id from courses where course_name = 'Python разработчик. Advanced'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-2-python-adv', '2025-02-09', '2025-07-09', 'ACTIVE', (select id from courses where course_name = 'Python разработчик. Advanced'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-1-golang', '2025-01-09', '2025-06-09', 'ACTIVE', (select id from courses where course_name = 'Golang разработчик. Basic'));

insert into groups (id, group_name, start_date, end_date, status, course_id)
values (uuid_generate_v4(), '2025-2-golang', '2025-02-09', '2025-07-09', 'ACTIVE', (select id from courses where course_name = 'Golang разработчик. Basic'));

insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Алексей', 'Иванов', 'Россия', 'aivanov@gmail.com', '+79933478899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Олег', 'Иванов', 'Россия', 'oivanov@gmail.com', '+79934478899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Никита', 'Филатов', 'Россия', 'nfilatov@gmail.com', '+79933578899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Андрей', 'Андриянов', 'Россия', 'aandrianov@gmail.com', '+79933478199');

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Алексей Иванов'), (select id from groups where group_name = '2025-1-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Олег Иванов'), (select id from groups where group_name = '2025-1-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Никита Филатов'), (select id from groups where group_name = '2025-1-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Андрей Андриянов'), (select id from groups where group_name = '2025-1-java'));

insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Александр', 'Овечкин', 'США', 'aovechkin@gmail.com', '+19933478899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Матвей', 'Мичков', 'США', 'mmichkov@gmail.com', '+19934478899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Фёдор', 'Емельянов', 'Россия', 'femeolyanov@gmail.com', '+79933672899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Елизавета', 'Наумова', 'Россия', 'enaumova@gmail.com', '+79993488190');

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Александр Овечкин'), (select id from groups where group_name = '2025-2-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Матвей Мичков'), (select id from groups where group_name = '2025-2-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Фёдор Емельянов'), (select id from groups where group_name = '2025-2-java'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Елизавета Наумова'), (select id from groups where group_name = '2025-2-java'));

insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Иван', 'Климов', 'Россия', 'iklimov@gmail.com', '+79933478849');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Александра', 'Свешникова', 'Россия', 'asveshnikova@gmail.com', '+79934878899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Анастасия', 'Чернышева', 'Россия', 'achernysheva@gmail.com', '+79933672896');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Гордей', 'Малышев', 'Россия', 'gmalyshev@gmail.com', '+79993488197');

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Иван Климов'), (select id from groups where group_name = '2025-1-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Александра Свешникова'), (select id from groups where group_name = '2025-1-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Анастасия Чернышева'), (select id from groups where group_name = '2025-1-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Гордей Малышев'), (select id from groups where group_name = '2025-1-spring'));

insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Ева', 'Данилова', 'Россия', 'edanilova@gmail.com', '+79931478849');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Ольга', 'Королева', 'Россия', 'okoroleva@gmail.com', '+79924878899');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Софья', 'Пономарева', 'Россия', 'sponomaryova@gmail.com', '+79973672896');
insert into students (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Роман', 'Скворцов', 'Россия', 'rskvorcov@gmail.com', '+79992488197');

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Ева Данилова'), (select id from groups where group_name = '2025-2-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Ольга Королева'), (select id from groups where group_name = '2025-2-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Софья Пономарева'), (select id from groups where group_name = '2025-2-spring'));

insert into students_groups (student_id, group_id)
values ((select id from students where (first_name || ' ' || last_name) = 'Роман Скворцов'), (select id from groups where group_name = '2025-2-spring'));

insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Анна', 'Симонова', 'Россия', 'asimonova@gmail.com', '+79931438849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Милана', 'Жукова', 'Россия', 'mzhukova@gmail.com', '+79931468849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Михаил', 'Сальников', 'Россия', 'msalnikov@gmail.com', '+79931428849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Вероника', 'Иванова', 'Россия', 'vivanova@gmail.com', '+79331428849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Виктория', 'Виноградова', 'Россия', 'vvinogradova@gmail.com', '+79371498849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Михаил', 'Беляев', 'Россия', 'mbelyaev@gmail.com', '+79371450849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Анна', 'Коровина', 'Россия', 'akorovina@gmail.com', '+79371550849');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Валерия', 'Голубева', 'Россия', 'vgolubeva@gmail.com', '+79392550839');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Давид', 'Иванов', 'Россия', 'divanov@gmail.com', '+79392550251');
insert into teachers (id, first_name, last_name, country, email, phone_number) values (uuid_generate_v4(), 'Виктор', 'Иванов', 'Россия', 'vivanov@gmail.com', '+79392550252');

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Java разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Анна Симонова'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Java разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Милана Жукова'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Spring разработчик'), (select id from teachers where (first_name || ' ' || last_name) = 'Михаил Сальников'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Spring разработчик'), (select id from teachers where (first_name || ' ' || last_name) = 'Вероника Иванова'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Python разработчик. Advanced'), (select id from teachers where (first_name || ' ' || last_name) = 'Виктория Виноградова'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Python разработчик. Advanced'), (select id from teachers where (first_name || ' ' || last_name) = 'Михаил Беляев'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Golang разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Анна Коровина'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Golang разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Валерия Голубева'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Java разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Давид Иванов'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Spring разработчик'), (select id from teachers where (first_name || ' ' || last_name) = 'Давид Иванов'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Python разработчик. Advanced'), (select id from teachers where (first_name || ' ' || last_name) = 'Виктор Иванов'));

insert into courses_teachers (course_id, teacher_id)
values ((select id from courses where course_name = 'Golang разработчик. Basic'), (select id from teachers where (first_name || ' ' || last_name) = 'Виктор Иванов'));


insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Приложение hello world на Java', (select id from courses where course_name = 'Java разработчик. Basic'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Циклы и ветвления', (select id from courses where course_name = 'Java разработчик. Basic'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Основы spring-context', (select id from courses where course_name = 'Spring разработчик'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Использование Spring MVC', (select id from courses where course_name = 'Spring разработчик'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Основы Django framework', (select id from courses where course_name = 'Python разработчик. Advanced'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Django и Redis', (select id from courses where course_name = 'Python разработчик. Advanced'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Приложение hello world на Golang', (select id from courses where course_name = 'Golang разработчик. Basic'));

insert into homeworks_info (id, topic, course_id)
values (uuid_generate_v4(), 'Основы Goroutines', (select id from courses where course_name = 'Golang разработчик. Basic'));