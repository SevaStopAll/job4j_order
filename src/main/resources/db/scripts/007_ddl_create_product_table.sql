create table IF NOT EXISTS product(
    id serial primary key not null,
    name varchar(2000),
    description varchar(2000),
    price integer
);

comment on table product is '������� ����, ��������� � ������';
comment on column product.id is '������������� �����';
comment on column product.name is '�������� �����';
comment on column product.description is '�������� �����';
comment on column product.price is '���� �����';