create table IF NOT EXISTS product(
    id serial primary key not null,
    name varchar(2000),
    description varchar(2000),
    price integer
);

comment on table product is 'Таблица блюд, доступных к заказу';
comment on column product.id is 'Идентификатор блюда';
comment on column product.name is 'Название блюда';
comment on column product.description is 'Описание блюда';
comment on column product.price is 'Цена блюда';