create table IF NOT EXISTS orders(
    id serial primary key not null,
    status int not null references status(id) default 1,
    customer int not null references customer(id),
    pay_method int not null references pay_methods(id),
    price integer
);

comment on table orders is 'Таблица сделанных заказов';
comment on column orders.id is 'Идентификатор заказа';
comment on column orders.status is 'Статус заказа';
comment on column orders.customer is 'Идентификатор клиента, сделавшего заказ';
comment on column orders.pay_method is 'Выбранный клиентом способ оплаты заказа';
comment on column orders.price is 'Стоимость заказа';