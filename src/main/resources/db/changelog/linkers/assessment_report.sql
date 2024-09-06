CREATE TABLE IF NOT EXISTS  bing.assessment_report
(
    id            UUID PRIMARY KEY,
    assessment_id UUID NOT NULL,
    report_id     UUID NOT NULL,
    CONSTRAINT fk_assessment
        FOREIGN KEY (assessment_id)
            REFERENCES bing.assessment (id),
    CONSTRAINT fk_report
        FOREIGN KEY (report_id)
            REFERENCES bing.report (id)
);
