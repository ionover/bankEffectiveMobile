--liquibase formatted sql
--changeset ionov:003-insertSystemAdmin

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
