--liquibase formatted sql
--changeset ionov:004-protectCardNumber
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = current_schema() AND table_name = 'cards' AND column_name = 'number_encrypted';

ALTER TABLE cards
    ADD COLUMN number_encrypted TEXT NOT NULL,
    ADD COLUMN number_hash VARCHAR(64) NOT NULL,
    ADD COLUMN number_last4 VARCHAR(4) NOT NULL;

ALTER TABLE cards
    ADD CONSTRAINT cards_number_hash_format_check
        CHECK (number_hash ~ '^[0-9a-f]{64}$'),
    ADD CONSTRAINT cards_number_last4_format_check
        CHECK (number_last4 ~ '^[0-9]{4}$');

CREATE UNIQUE INDEX ux_cards_number_hash
    ON cards (number_hash);

ALTER TABLE cards
    DROP COLUMN number;

COMMENT ON COLUMN cards.number_encrypted IS 'Encrypted normalized card number';
COMMENT ON COLUMN cards.number_hash IS 'HMAC-SHA256 hash of normalized card number for uniqueness and exact lookup';
COMMENT ON COLUMN cards.number_last4 IS 'Last four digits of normalized card number for masked responses';

--rollback DROP INDEX IF EXISTS ux_cards_number_hash;
--rollback ALTER TABLE cards ADD COLUMN number VARCHAR(255);
--rollback ALTER TABLE cards DROP CONSTRAINT IF EXISTS cards_number_last4_format_check;
--rollback ALTER TABLE cards DROP CONSTRAINT IF EXISTS cards_number_hash_format_check;
--rollback ALTER TABLE cards DROP COLUMN IF EXISTS number_last4;
--rollback ALTER TABLE cards DROP COLUMN IF EXISTS number_hash;
--rollback ALTER TABLE cards DROP COLUMN IF EXISTS number_encrypted;
