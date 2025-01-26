create table if not exists resurs.transaction (
    id bigserial primary key,
    customer_id bigint not null,
    amount numeric not null,
    transaction_date timestamp not null,
    created timestamp not null default current_timestamp,

    foreign key (customer_id) references resurs.customerEntity (id)
);