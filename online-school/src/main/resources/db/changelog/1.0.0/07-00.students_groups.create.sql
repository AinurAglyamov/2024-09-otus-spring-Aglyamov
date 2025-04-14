CREATE TABLE IF NOT EXISTS students_groups
(
    student_id uuid not null references students (id),
    group_id   uuid not null references groups (id),
    primary key (student_id, group_id)
);
