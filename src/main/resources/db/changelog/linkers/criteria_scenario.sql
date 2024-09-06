CREATE TABLE IF NOT EXISTS  bing.criteria_scenario
(
    id          UUID PRIMARY KEY,
    scenario_id UUID NOT NULL,
    criteria_id UUID NOT NULL,
    CONSTRAINT fk_scenario
        FOREIGN KEY (scenario_id)
            REFERENCES bing.scenario (id),
    CONSTRAINT fk_criteria
        FOREIGN KEY (criteria_id)
            REFERENCES bing.criteria_group (id)
);
