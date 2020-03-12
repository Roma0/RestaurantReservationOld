CREATE TABLE reviews (
    id                  BIGSERIAL NOT NULL,
    description         VARCHAR(500),
    restaurant_id       BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    reservation_id      BIGINT NOT NULL
);

ALTER TABLE reviews ADD CONSTRAINT review_pk PRIMARY KEY (id);

ALTER TABLE reviews
    ADD CONSTRAINT review_restaurant_fk FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id);

ALTER TABLE reviews
    ADD CONSTRAINT review_user_fk FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE reviews
    ADD CONSTRAINT review_reservation_fk FOREIGN KEY (reservation_id)
        REFERENCES reservations (id);
