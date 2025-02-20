CREATE TABLE IF NOT EXISTS bing.responsibility_class
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    term_of_use_factor VARCHAR(255) NOT NULL,
    correction_factor  VARCHAR(255) NOT NULL
);
