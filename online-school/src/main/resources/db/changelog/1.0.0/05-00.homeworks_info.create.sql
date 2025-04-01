CREATE TABLE IF NOT EXISTS homeworks_info
(
    id         uuid primary key,
    topic      varchar     not null,
    course_id  uuid references courses (id),
    created_at timestamptz not null default current_timestamp,
    updated_at timestamptz
);
