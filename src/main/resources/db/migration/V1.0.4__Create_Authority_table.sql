CREATE TABLE authority(
  id                    BIGSERIAL NOT NULL,
  role                  VARCHAR(30) NOT NULL,
  allowed_resource      VARCHAR(300),
  allowed_read          BOOLEAN NOT NULL default false,
  allowed_create        BOOLEAN NOT NULL default false,
  allowed_update        BOOLEAN NOT NULL default false,
  allowed_delete        BOOLEAN NOT NULL default false
);

ALTER TABLE authority ADD CONSTRAINT authority_pk PRIMARY KEY (id);