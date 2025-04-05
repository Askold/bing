CREATE TABLE IF NOT EXISTS bing.accident_probability
(
    id                          UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    safety_scenario_group_state VARCHAR(255)     NOT NULL,
    final_safety_level          VARCHAR(255)     NOT NULL,
    responsibility_class_id     UUID             NOT NULL,
    accident_probability_value  DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_resp_class
        FOREIGN KEY (responsibility_class_id)
            REFERENCES bing.responsibility_class (id),

    CONSTRAINT unique_resp_class_final_safety_level
        UNIQUE (final_safety_level, responsibility_class_id)
);