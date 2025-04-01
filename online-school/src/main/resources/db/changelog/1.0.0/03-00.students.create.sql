CREATE TABLE IF NOT EXISTS students
(
    id           uuid primary key,
    first_name   varchar     not null,
    last_name    varchar     not null,
    country      varchar,
    email        varchar,
    phone_number varchar,
    created_at   timestamptz not null default current_timestamp,
    updated_at   timestamptz
);