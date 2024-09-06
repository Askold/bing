CREATE TABLE IF NOT EXISTS bing.criteria_template
(
    id          UUID PRIMARY KEY,
    template_id UUID NOT NULL,
    criteria_id UUID NOT NULL,
    rank        INT,
    CONSTRAINT fk_criteria
        FOREIGN KEY (criteria_id)
            REFERENCES bing.criteria (id)
);