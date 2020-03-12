CREATE TABLE users(
  id                    BIGSERIAL NOT NULL,
  name                  VARCHAR(30) UNIQUE NOT NULL,
  first_name            VARCHAR(30),
  last_name             VARCHAR(30),
  email                 VARCHAR(50) UNIQUE NOT NULL,
  password              VARCHAR(50),
  phone                 VARCHAR(15)
);

ALTER TABLE users ADD CONSTRAINT user_pk primary KEY (id);