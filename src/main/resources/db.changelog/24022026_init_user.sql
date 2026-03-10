CREATE TABLE jiro_user.user
(
    id serial NOT NULL,
    login varchar NOT NULL,
    password varchar NOT NULL,
    is_deleted boolean not null DEFAULT false,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX user_unix01 ON jiro_user.user(login)
WHERE deleted_at IS FALSE;