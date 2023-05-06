create table IF NOT EXISTS status(
    id serial primary key not null,
    name varchar(2000) not null
);

comment on table status is 'Таблица статусов заказа';
comment on column status.id is 'Идентификатор статуса';
comment on column status.name is 'Название статуса';