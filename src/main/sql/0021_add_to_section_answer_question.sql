ALTER TABLE employmeo.survey_sections ADD ss_name text;
ALTER TABLE employmeo.answers ADD answer_corefactor_id bigint;
ALTER TABLE employmeo.questions ADD question_scoring_model text;

--//@UNDO

ALTER TABLE employmeo.questions DROP question_scoring_model;
ALTER TABLE employmeo.answers DROP answer_corefactor_id;
ALTER TABLE employmeo.survey_sections DROP ss_name;