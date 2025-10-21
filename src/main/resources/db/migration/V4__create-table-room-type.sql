CREATE TABLE IF NOT EXISTS room_type (
    id bigserial PRIMARY KEY,
    name varchar(255) not null,
    capacity int not null,
    bedConfig varchar(500) not null,
    amenities TEXT not null,
    basePrice numeric(10,2) not null,
    category room_category_enum not null
);