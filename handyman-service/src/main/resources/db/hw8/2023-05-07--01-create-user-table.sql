create table handyman_user
(
    id         bigserial primary key,
    first_name text  not null,
    last_name  text  not null,
    email      text  not null,
    phone      text,
    photo      bytea
);

create table skill
(
    id      bigserial primary key,
    handyman_user_id bigint not null,
    name    text   not null,
    foreign key (handyman_user_id) references handyman_user (id)
);
