--liquibase formatted sql

--changeset dkutuzov:1
create table if not exists gardener
(
    id         bigserial primary key,
    first_name text not null,
    last_name  text not null,
    email      text not null,
    phone      text
);
