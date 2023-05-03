create table IF NOT EXISTS pay_methods(
    id serial primary key not null,
    name varchar(2000) not null
);