CREATE TABLE IF NOT EXISTS bing.criteria (
                          id UUID PRIMARY KEY,   -- Primary key with UUID generation
                          code VARCHAR(255) NOT NULL,                       -- Criterion code, required
                          full_name VARCHAR(255) NOT NULL,                   -- Full name of the criterion, required
                          k1 VARCHAR(255) NOT NULL,                         -- Indicator K1, required
                          k2 VARCHAR(255) NOT NULL,                         -- Indicator K2, required
                          k3 VARCHAR(255) NOT NULL,                         -- Indicator K3, required
                          criteria_group_id UUID NOT NULL,                 -- Foreign key to criteria_groups, required
                          CONSTRAINT fk_criteria_group FOREIGN KEY (criteria_group_id) REFERENCES bing.criteria_group(id)
);
