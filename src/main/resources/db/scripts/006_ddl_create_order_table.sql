create table order(
    id serial primary key not null,
    status int not null reference status(id) default 1,
    customer int not null reference customer(id),

)