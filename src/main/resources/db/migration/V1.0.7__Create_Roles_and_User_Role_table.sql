CREATE TABLE Roles (
    id                      BIGSERIAL NOT NULL,
    role                    VARCHAR(30) UNIQUE NOT NULL,
    allowed_read_resources   VARCHAR(225),
    allowed_create_resources VARCHAR(225),
    allowed_update_resources VARCHAR(225),
    allowed_delete_resources VARCHAR(225),
    CONSTRAINT role_pk PRIMARY KEY (id)
);

ALTER TABLE Authority ADD CONSTRAINT authority_role_fk FOREIGN KEY (role) REFERENCES Roles (role);

CREATE TABLE Users_Roles(
    user_id             BIGINT NOT NULL,
    role_id             BIGINT NOT NULL,
    CONSTRAINT users_fk
        FOREIGN KEY (user_id)
        REFERENCES Users (id),
    CONSTRAINT roles_fk
        FOREIGN KEY (role_id)
        REFERENCES Roles (id)
);