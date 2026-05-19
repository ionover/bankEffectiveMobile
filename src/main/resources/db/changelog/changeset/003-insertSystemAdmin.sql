--liquibase formatted sql
--changeset ionov:003-insertSystemAdmin
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM users WHERE login = 'systemAdmin@mail.com';

INSERT INTO users(login,
                  password,
                  name,
                  surname,
                  is_admin)
VALUES ('systemAdmin@mail.com',
        '${system_admin_password_hash}',
        'system',
        'admin',
        true);
