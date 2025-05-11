CREATE TABLE reborn.transaction_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_date DATE,
    transaction_time TIME,
    code VARCHAR(2),
    debit_credit VARCHAR(255),
    channel VARCHAR(50),
    balance DECIMAL(18, 2),
    remark VARCHAR(255),
    user_account VARCHAR(20),
    CONSTRAINT fk_user_account FOREIGN KEY (user_account) REFERENCES reborn.user_info(account)
);