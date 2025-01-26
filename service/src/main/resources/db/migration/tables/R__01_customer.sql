create table if not exists resurs.customer (
    id bigserial primary key,
    name text not null,
    social_security_number text not null,
    credit_score integer not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);