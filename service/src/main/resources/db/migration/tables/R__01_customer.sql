create table if not exists resurs.customerEntity (
    id bigserial primary key,
    name text not null,
    social_security_number text not null,
    credit_score integer not null
);