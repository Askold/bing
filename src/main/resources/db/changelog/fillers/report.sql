CREATE TABLE IF NOT EXISTS bing.report
(
    id           UUID PRIMARY KEY,
    object_id    UUID         NOT NULL,
    json_values  VARCHAR,
    author_login VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    modified_at  TIMESTAMP,
    template_id  UUID         NOT NULL,
    CONSTRAINT fk_template
        FOREIGN KEY (template_id)
            REFERENCES bing.template (id),
    CONSTRAINT fk_hydro_object
        FOREIGN KEY (object_id)
            REFERENCES bing.hydro_object (id)
);