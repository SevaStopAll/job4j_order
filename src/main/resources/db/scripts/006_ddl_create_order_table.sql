create table IF NOT EXISTS orders(
    id serial primary key not null,
    status int not null references status(id) default 1,
    customer int not null references customer(id)
);