create table IF NOT EXISTS card(
    id serial primary key not null,
    customer_id int not null references customer(id)
);

comment on table card is '������� ���������� ���� ����������';
comment on column card.id is '������������� ����� ����������';
comment on column card.customer_id is '������������� ������� - ��������� ����� ����������';