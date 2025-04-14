CREATE TABLE IF NOT EXISTS homeworks
(
    id                uuid primary key,
    student_id        uuid        not null references students (id),
    teacher_id        uuid references teachers (id),
    homeworks_info_id uuid        not null references homeworks_info (id),
    status            varchar     not null,
    start_date        date,
    end_date          date,
    mark              smallint,
    created_at        timestamptz not null default current_timestamp,
    updated_at        timestamptz
);