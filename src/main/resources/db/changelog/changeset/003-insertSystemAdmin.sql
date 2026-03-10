--liquibase formatted sql
--changeset ionov:003-insertSystemAdmin

INSERT INTO users(login,
                  password,
                  name,
                  surname,
                  is_admin)
VALUES ('systemAdmin@mail.com',
        '$2a$10$6tAWZNv7mz.o9fPV9BtegeFZpB7e2CcLex4UE/QU9C6Cfp2R3jbuW',
        'system',
        'admin',
        true);
