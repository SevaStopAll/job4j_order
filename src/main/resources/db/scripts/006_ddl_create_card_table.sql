create table IF NOT EXISTS card(
    id serial primary key not null,
    customer_id int not null references customer(id)
);

comment on table card is 'Таблица клиентских карт лояльности';
comment on column card.id is 'Идентификатор карты лояльности';
comment on column card.customer_id is 'Идентификатор клиента - владельца карты лояльности';