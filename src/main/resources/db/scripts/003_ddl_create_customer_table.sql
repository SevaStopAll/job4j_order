create table status(
    id serial primary key not null,
    name varchar(256) not null unique,
    password varchar(128) not null
)