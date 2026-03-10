--liquibase formatted sql
--changeset ionov:001-createCard

CREATE TABLE cards
(
    id              BIGSERIAL    PRIMARY KEY,
    number          VARCHAR(255) NOT NULL,
    owner           BIGINT       NOT NULL,
    validity_period timestamp    NOT NULL,
    status          VARCHAR(255) NOT NULL,
    balance         BIGINT       NOT NULL
);
