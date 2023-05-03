create table IF NOT EXISTS product(
    id serial primary key not null,
    name varchar(2000),
    description varchar(2000),
    price integer
);