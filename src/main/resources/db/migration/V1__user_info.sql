CREATE TABLE phoenix.user_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title NVARCHAR(20),
    first_name NVARCHAR(50),
    last_name NVARCHAR(50),
    middle_name NVARCHAR(50),
    age INT,
    birthdate NVARCHAR(50),
    disable_status BOOLEAN,
    married_status BOOLEAN,
    update_date DATETIME,
    update_by NVARCHAR(50),
    create_date DATETIME,
    create_by NVARCHAR(50),
    status_active BOOLEAN
);
