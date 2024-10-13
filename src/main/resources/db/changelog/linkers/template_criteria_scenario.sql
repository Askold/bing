CREATE TABLE IF NOT EXISTS bing.template_criteria_scenario
(
    id                       UUID PRIMARY KEY,
    template_id              UUID    NOT NULL,
    criteria_id              UUID    NOT NULL,
    rank                     INT,
    scenario_id              UUID    NOT NULL,
    significance_coefficient INTEGER NOT NULL,
    criteries_rating         INTEGER NOT NULL,
    criteries_rating_final   INTEGER NOT NULL,
    fact_value               INTEGER NOT NULL,

    CONSTRAINT fk_criteria
        FOREIGN KEY (criteria_id)
            REFERENCES bing.criteria (id),
    CONSTRAINT fk_scenario
        FOREIGN KEY (scenario_id)
            REFERENCES bing.scenario (id),
    CONSTRAINT fk_template
        FOREIGN KEY (template_id)
            REFERENCES bing.template (id)
);