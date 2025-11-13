CREATE TABLE IF NOT EXISTS room (
    id BIGSERIAL PRIMARY KEY,
    code varchar(50) UNIQUE NOT NULL,
    floor varchar(50) NOT NULL,
    customPrice numeric(10, 2) NOT NULL,
    status varchar(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT false,

    room_type_id BIGINT NOT NULL,

    CONSTRAINT fk_room_type FOREIGN KEY (room_type_id) REFERENCES room_type (id)
);