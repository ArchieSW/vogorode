--liquibase formatted sql

--changeset dkutuzov:3
create table if not exists banks
(
    id   bigserial primary key,
    name text not null
);

insert into banks(name)
values ('InterGalactic Banking Clan'),
       ('Gringotts'),
       ('ScaminaBank'),
       ('Trinance'),
       ('StonksBank'),
       ('DogeBank'),
       ('PepegaBank'),
       ('ToTheMoonBank'),
       ('RickRollBank'),
       ('YetAnotherBank');

alter table account
    add column bank_id bigint null references banks (id);