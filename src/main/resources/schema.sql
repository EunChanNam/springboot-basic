CREATE TABLE customers
(
    customer_id    BIGINT PRIMARY KEY,
    name           varchar(20) NOT NULL,
    email          varchar(50) NOT NULL,
    last_login_at  datetime    DEFAULT NULL,
    created_at     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id      BIGINT PRIMARY KEY,
    voucher_type    varchar(20) NOT NULL,
    fixed_amount    double  DEFAULT NULL,
    percent_amount  double  DEFAULT NULL
);

