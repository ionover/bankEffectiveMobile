--liquibase formatted sql
--changeset ionov:001-createCard
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = current_schema() AND table_name = 'cards';

CREATE TABLE cards
(
    id              BIGSERIAL PRIMARY KEY,
    number          VARCHAR(255) NOT NULL,
    owner           BIGINT       NOT NULL,
    validity_period timestamp    NOT NULL,
    status          VARCHAR(255) NOT NULL,
    balance         BIGINT       NOT NULL,
    created_at       timestamp without time zone DEFAULT now(),
    updated_at       timestamp without time zone,
    version         BIGINT       NOT NULL DEFAULT 0
);
