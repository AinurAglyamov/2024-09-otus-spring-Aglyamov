CREATE TABLE IF NOT EXISTS groups
(
    id         uuid primary key,
    group_name varchar     not null,
    start_date date,
    end_date   date,
    status     varchar,
    course_id  uuid references courses (id),
    created_at timestamptz not null default current_timestamp,
    updated_at timestamptz
);