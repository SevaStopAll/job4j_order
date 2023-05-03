create table IF NOT EXISTS card(
    id serial primary key not null,
    customer_id int not null references customer(id)
);