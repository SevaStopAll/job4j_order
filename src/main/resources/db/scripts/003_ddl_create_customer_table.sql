create table IF NOT EXISTS customer(
    id serial primary key not null,
    name varchar(256) not null unique,
    password varchar(128) not null
);