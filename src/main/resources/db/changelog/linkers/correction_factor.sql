CREATE TABLE IF NOT EXISTS bing.correction_factor
(
    id          UUID PRIMARY KEY,
    class_id    UUID NOT NULL,
    scenario_id UUID NOT NULL,
    value       DOUBLE PRECISION,
    CONSTRAINT fk_class
        FOREIGN KEY (class_id)
            REFERENCES bing.responsibility_class (id),
    CONSTRAINT fk_scenario
        FOREIGN KEY (scenario_id)
            REFERENCES bing.scenario (id)
);
