--liquibase formatted sql

--changeset dkutuzov:1
create extension if not exists "uuid-ossp";

create table if not exists users
(
    id              uuid default uuid_generate_v4() primary key,
    user_type       text                        not null,
    login           text                        not null,
    email           text                        not null,
    phone_number    text                        not null,
    created_at      timestamp without time zone not null,
    last_updated_at timestamp without time zone not null
);
