create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

create table comments (
      id bigserial,
      text varchar(255),
      comment_date date,
      book_id bigint references books(id) on delete cascade,
      primary key (id)
);

create table users (
      id bigserial primary key,
      login varchar,
      password varchar,
      role varchar
);

CREATE TABLE IF NOT EXISTS acl_sid (
       id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
       principal int NOT NULL,
       sid varchar(100) NOT NULL,
       PRIMARY KEY (id),
       UNIQUE (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
     id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
     class varchar(255) NOT NULL,
     PRIMARY KEY (id),
     UNIQUE (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
     id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
     acl_object_identity bigint NOT NULL,
     ace_order int NOT NULL,
     sid bigint NOT NULL,
     mask int NOT NULL,
     granting int NOT NULL,
     audit_success int NOT NULL,
     audit_failure int NOT NULL,
     PRIMARY KEY (id),
     UNIQUE (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
   id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
   object_id_class bigint NOT NULL,
   object_id_identity bigint NOT NULL,
   parent_object bigint DEFAULT NULL,
   owner_sid bigint DEFAULT NULL,
   entries_inheriting int NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);