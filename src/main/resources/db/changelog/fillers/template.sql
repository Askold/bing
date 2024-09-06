CREATE TABLE IF NOT EXISTS  bing.template
(
    id           UUID PRIMARY KEY,
    object_id    UUID         NOT NULL,
    author_login VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    modified_at  TIMESTAMP,
    CONSTRAINT fk_object
        FOREIGN KEY (object_id)
            REFERENCES bing.hydro_object (id)
);