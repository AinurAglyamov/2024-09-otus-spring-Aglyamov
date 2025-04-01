CREATE TABLE IF NOT EXISTS homework_chat
(
    id          uuid primary key,
    homework_id uuid        not null references homeworks (id),
    message     varchar     not null,
    student_id  uuid references students (id),
    teacher_id  uuid references teachers (id),
    created_at  timestamptz not null default current_timestamp,
    updated_at  timestamptz
);
