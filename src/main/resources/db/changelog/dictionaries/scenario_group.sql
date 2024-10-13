CREATE TABLE IF NOT EXISTS bing.scenario_group
(
    id           UUID            PRIMARY KEY,
    name         VARCHAR(255)    NOT NULL,
    object_id    UUID            NOT NULL,
    danger_koef  INTEGER         NOT NULL,

    CONSTRAINT fk_object
        FOREIGN KEY (object_id)
            REFERENCES bing.hydro_object (id)
);