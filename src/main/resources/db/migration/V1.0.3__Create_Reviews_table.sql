CREATE TABLE reviews (
    id                  BIGSERIAL NOT NULL,
    description         VARCHAR(500),
    created_time        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT TRANSACTION_TIMESTAMP(),
    restaurant_id       BIGINT NOT NULL,
    user_id             BIGINT NOT NULL
);

ALTER TABLE reviews ADD CONSTRAINT review_pk PRIMARY KEY (id);

ALTER TABLE reviews
    ADD CONSTRAINT review_restaurant_fk FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id);

ALTER TABLE reviews
    ADD CONSTRAINT review_user_fk FOREIGN KEY (user_id)
        REFERENCES users (id);
