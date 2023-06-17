--liquibase formatted sql

--changeset dkutuzov:6

alter table gardener
    add column login text null;

update gardener set login = u.login from users u where u.email = gardener.email;