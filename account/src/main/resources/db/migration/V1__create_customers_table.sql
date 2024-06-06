CREATE TABLE customers (
    id_customers BIGSERIAL PRIMARY KEY,
    bank_account_id BIGINT UNIQUE,
    phone_number varchar(255) UNIQUE,
    password varchar(255)
);

INSERT INTO customers (bank_account_id, phone_number, password) VALUES
(1, '89956391506', 'good_password'),
(2, '89373877768', 'bad_password'),
(3, '7777777', 'password');
