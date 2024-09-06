CREATE TABLE IF NOT EXISTS bing.object_scenario
(
    id          UUID PRIMARY KEY,
    scenario_id UUID NOT NULL,
    object_id   UUID NOT NULL,
    CONSTRAINT fk_scenario
        FOREIGN KEY (scenario_id)
            REFERENCES bing.scenario (id),
    CONSTRAINT fk_object
        FOREIGN KEY (object_id)
            REFERENCES bing.hydro_object (id)
);
