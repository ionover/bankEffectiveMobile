--liquibase formatted sql
--changeset ionov:002-createUsers
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = current_schema() AND table_name = 'users';

CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    login       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    surname     VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    middle_name VARCHAR(255),
    phone       VARCHAR(255),
    is_admin    BOOLEAN      NOT NULL,
    created_at   timestamp without time zone DEFAULT now(),
    updated_at   timestamp without time zone,
    version BIGINT NOT NULL DEFAULT 0
);
