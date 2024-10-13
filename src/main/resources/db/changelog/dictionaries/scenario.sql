CREATE TABLE IF NOT EXISTS bing.scenario
(
    id                UUID PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    scenario_group_id UUID         NOT NULL,
    scenario_number   INTEGER,
    CONSTRAINT fk_scenario_group FOREIGN KEY (scenario_group_id)
        REFERENCES bing.scenario_group (id)
);