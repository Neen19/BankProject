CREATE TABLE bank_accounts(
    id_bank_account BIGSERIAL PRIMARY KEY,
    num_bank_account BIGINT UNIQUE,
    amount  NUMERIC(19, 2)
);

INSERT INTO bank_accounts (num_bank_account, amount) VALUES
(10, 1000.00),
(20, 2500.50),
(30, 500.75);