create table IF NOT EXISTS customer(
    id serial primary key not null,
    name varchar(256) not null unique,
    password varchar(128) not null,
    address varchar(2000) not null
);

comment on table customer is '������� ��������';
comment on column customer.id is '������������� �������';
comment on column customer.name is '��� �������';
comment on column customer.password is '������ �������';
comment on column customer.address is '����� �������';