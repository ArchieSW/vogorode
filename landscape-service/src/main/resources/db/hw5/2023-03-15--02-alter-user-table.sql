--liquibase formatted sql

--changeset dkutuzov:2
create table if not exists user_types
(
    id   smallserial primary key,
    type text not null
);

insert into user_types(id, type)
values (1, 'rancher'),
       (2, 'handyman');

alter table users
    add column user_type_id smallint not null references user_types (id) default 1;

update users
set user_type_id = (select id from user_types where user_types.type = users.user_type);

alter table users drop column user_type;