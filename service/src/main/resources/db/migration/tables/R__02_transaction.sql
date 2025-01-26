create table if not exists resurs.transaction (
    transaction_id bigserial primary key,
    customer_id bigint not null,
    amount numeric not null,
    transaction_type text not null,
    transaction_date timestamp not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp,

    foreign key (customer_id) references resurs.customer (id)
);

create index if not exists idx_transaction_customer_id on resurs.transaction (customer_id);