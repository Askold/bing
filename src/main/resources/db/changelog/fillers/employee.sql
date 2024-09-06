CREATE TABLE IF NOT EXISTS bing.employee
(
    login    VARCHAR(255) PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    role_id  VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    lead_id  VARCHAR(255),
    password VARCHAR(255) NOT NULL
);