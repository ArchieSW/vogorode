--liquibase formatted sql

--changeset dkutuzov:7
create extension if not exists "uuid-ossp";

create table if not exists user_types
(
    id   smallserial primary key,
    type text not null
);

insert into user_types(id, type)
values (1, 'rancher'),
       (2, 'handyman');
