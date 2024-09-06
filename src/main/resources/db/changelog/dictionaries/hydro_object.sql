CREATE TABLE IF NOT EXISTS  bing.hydro_object
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    class_id UUID         NOT NULL,
    CONSTRAINT fk_class
        FOREIGN KEY (class_id)
            REFERENCES bing.responsibility_class (id)
);
