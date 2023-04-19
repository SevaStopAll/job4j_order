create table card(
    id serial primary key not null,
    customer_id int not null reference customer(id)
)