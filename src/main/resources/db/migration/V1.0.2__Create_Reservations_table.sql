CREATE TABLE reservations (
  id                    BIGSERIAL NOT NULL,
  created_time          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT TRANSACTION_TIMESTAMP(),
  update_time           TIMESTAMP WITH TIME ZONE DEFAULT TRANSACTION_TIMESTAMP(),
  reserved_time         TIMESTAMP WITH TIME ZONE NOT NULL,
  num_persons           INTEGER,
  reserved_status       INTEGER NOT NULL default 0,
  restaurant_id         BIGINT NOT NULL,
  user_id               BIGINT NOT NULL
);

ALTER TABLE reservations ADD CONSTRAINT reservation_pk PRIMARY KEY (id);

ALTER TABLE reservations
    ADD CONSTRAINT reservation_restaurant_fk FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id);

ALTER TABLE reservations
    ADD CONSTRAINT reservation_user_fk FOREIGN KEY (user_id)
        REFERENCES users (id);