create table IF NOT EXISTS customer(
    id serial primary key not null,
    name varchar(256) not null unique,
    password varchar(128) not null,
    address varchar(2000) not null
);

comment on table customer is 'Таблица клиентов';
comment on column customer.id is 'Идентификатор клиента';
comment on column customer.name is 'Имя клиента';
comment on column customer.password is 'Пароль клиента';
comment on column customer.address is 'Адрес клиента';