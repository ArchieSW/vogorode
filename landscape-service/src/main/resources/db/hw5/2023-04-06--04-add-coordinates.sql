--liquibase formatted sql

--changeset dkutuzov:4
ALTER TABLE users ADD COLUMN latitude double precision;
ALTER TABLE users ADD COLUMN longitude double precision;
