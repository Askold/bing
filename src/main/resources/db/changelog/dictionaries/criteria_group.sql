CREATE TABLE IF NOT EXISTS  bing.criteria_group
(
    id       UUID PRIMARY KEY,     -- Идентификатор группы критериев
    full_name VARCHAR(255) NOT NULL -- Полное наименование группы критериев
);