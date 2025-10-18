CREATE TABLE IF NOT EXISTS client (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null,
    pin varchar(12) not null,
    email varchar(255) not null unique,
    date_of_birth date not null,
    phone_number varchar(15) not null unique,
    street varchar(100) not null,
    neighborhood varchar(100) not null,
    city varchar(50) not null,
    number varchar(6),
    complement varchar(50),
    state char(2) not null,
    postal_code char(8) not null
);