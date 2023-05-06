create table IF NOT EXISTS order_product(
    id serial primary key,
    order_id int not null references orders(id),
    product_id int not null references product(id)
);

comment on table order_product is 'Таблица заказов и блюд, находящихся в заказе';
comment on column orders.id is 'Идентификатор заказа';
comment on column orders.status is 'Идентификатор блюда';