CREATE TABLE restaurants (
    id                  BIGSERIAL NOT NULL,
    name                VARCHAR (50) UNIQUE NOT NULL,
    description         VARCHAR (450),
    address             VARCHAR (150) NOT NULL,
    open_time           TIME NOT NULL,
    close_time          TIME NOT NULL,
    phone               VARCHAR (15)
);

ALTER TABLE restaurants ADD CONSTRAINT restaurant_pk primary key (id);