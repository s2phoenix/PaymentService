CREATE TABLE reborn.user_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    thai_name VARCHAR(50) NOT NULL,
    eng_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    pin VARCHAR(6) NOT NULL,
    role VARCHAR(20) NOT NULL,
    account VARCHAR(20) NOT NULL UNIQUE,
    password TEXT,
    update_date DATETIME,
    update_by VARCHAR(50),
    create_date DATETIME,
    create_by VARCHAR(50),
    status VARCHAR(20)
);