--liquibase formatted sql
--changeset ionov:003-insertSystemAdmin

INSERT INTO users(login,
                  password,
                  name,
                  surname,
                  is_admin)
VALUES ('systemAdmin@mail.com',
        '$2b$12$u7pOdf9Doe0rSc.ArrN/aOiAXSSYAbCKyvf0GYREhn0hc6DDHDImu',
        'system',
        'admin',
        true);
