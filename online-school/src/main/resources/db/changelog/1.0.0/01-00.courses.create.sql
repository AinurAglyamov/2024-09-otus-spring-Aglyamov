CREATE TABLE IF NOT EXISTS courses
(
    id          uuid primary key,
    course_name varchar     not null,
    cost        numeric     not null,
    description varchar,
    created_at  timestamptz not null default current_timestamp,
    updated_at  timestamptz
);
