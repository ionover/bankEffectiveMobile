--liquibase formatted sql
--changeset ionov:002-createUsers

CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    login       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    phone       VARCHAR(255),
    is_admin    BOOLEAN      NOT NULL
);
