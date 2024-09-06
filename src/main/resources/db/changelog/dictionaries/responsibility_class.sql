CREATE TABLE IF NOT EXISTS  bing.responsibility_class
(
    id                  UUID PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    significance_factor VARCHAR(255) NOT NULL
);
