create table IF NOT EXISTS order_product(
    id serial primary key,
    order_id int not null references orders(id),
    product_id int not null references product(id)
);