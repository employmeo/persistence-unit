ALTER TABLE employmeo.account_surveys ALTER as_preamble_text TYPE text;
ALTER TABLE employmeo.account_surveys ALTER as_thankyou_text TYPE text;
ALTER TABLE employmeo.answers ALTER answer_text TYPE text;
ALTER TABLE employmeo.corefactor_descriptions ALTER cf_description TYPE text;
ALTER TABLE employmeo.corefactors ALTER corefactor_description TYPE text;
ALTER TABLE employmeo.questions ALTER question_text TYPE text;
ALTER TABLE employmeo.questions ALTER question_description TYPE text;
ALTER TABLE employmeo.survey_sections ALTER ss_instructions TYPE text;
ALTER TABLE employmeo.surveys ALTER survey_description TYPE text;

--//@UNDO

ALTER TABLE employmeo.surveys ALTER survey_description TYPE character varying(1083);
ALTER TABLE employmeo.survey_sections ALTER ss_instructions TYPE character varying(1083);
ALTER TABLE employmeo.questions ALTER question_description TYPE character varying(255)
ALTER TABLE employmeo.questions ALTER question_text TYPE character varying(800;
ALTER TABLE employmeo.corefactors ALTER corefactor_description TYPE character varying(255);
ALTER TABLE employmeo.corefactor_descriptions ALTER cf_description TYPE character varying(1083);
ALTER TABLE employmeo.answers ALTER answer_text TYPE character varying(1083);
ALTER TABLE employmeo.account_surveys ALTER as_thankyou_text TYPE character varying(2083);
ALTER TABLE employmeo.account_surveys ALTER as_preamble_text TYPE character varying(2083);