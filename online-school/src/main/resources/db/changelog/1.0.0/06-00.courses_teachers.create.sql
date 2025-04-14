CREATE TABLE IF NOT EXISTS courses_teachers
(
    course_id  uuid not null references courses (id),
    teacher_id uuid not null references teachers (id),
    primary key (course_id, teacher_id)
);