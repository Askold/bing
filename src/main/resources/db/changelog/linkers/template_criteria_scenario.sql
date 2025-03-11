CREATE TABLE IF NOT EXISTS bing.template_criteria_scenario
(
    id                        UUID PRIMARY KEY,
    template_id               UUID    NOT NULL,
    criteria_id               UUID    NOT NULL,
    rank                      INT,
    scenario_id               UUID    NOT NULL,
    significance_coefficient  FLOAT   NOT NULL,
    criteries_rating          FLOAT,
    criteries_rating_final    FLOAT,
    fact_value                varchar NOT NULL,
    is_criteries_rating_final boolean not null default false,
    assessment_id             uuid,

    CONSTRAINT fk_criteria
        FOREIGN KEY (criteria_id)
            REFERENCES bing.criteria (id),
    CONSTRAINT fk_scenario
        FOREIGN KEY (scenario_id)
            REFERENCES bing.scenario (id),
    CONSTRAINT fk_template
        FOREIGN KEY (template_id)
            REFERENCES bing.template (id),
    CONSTRAINT fk_assessment
        FOREIGN KEY (assessment_id)
            REFERENCES bing.assessment (id)
);