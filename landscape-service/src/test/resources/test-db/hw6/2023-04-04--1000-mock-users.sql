--liquibase formatted sql

--changeset dkutuzov:1000
insert into users(id, login, email, phone_number, created_at, last_updated_at, user_type_id, longitude, latitude)
values ('fa80750a-d06f-4e15-b50a-08fcd091a543', 'archie', 'archie@ya.ru', '+79371233221', '2023-04-04T23:53:00.528948685', '2023-04-04T23:53:00.528948685', 1, 1.1, 2.3),
       ('53b04327-f926-46e4-9b87-9fbea29bb2f2', 'archibald', 'archiensky@ya.ru', '+79371233221', '2023-04-04T23:53:00.528948685', '2023-04-04T23:53:00.528948685', 2, 2.1, 4.4);
