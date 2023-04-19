create table order_product(
    id serial primary key,
    order_id int not null reference order(id),
    product_id int not null reference product(id)

)