ALTER TABLE employmeo.account_surveys ADD as_static_link_view text;
ALTER TABLE employmeo.account_surveys ADD as_invite_template_id text;
ALTER TABLE employmeo.survey_sections ADD ss_top_instructions text;
ALTER TABLE employmeo.users ADD user_ats_id text;

--//@UNDO

ALTER TABLE employmeo.users DROP user_ats_id;
ALTER TABLE employmeo.survey_sections DROP ss_top_instructions;
ALTER TABLE employmeo.account_surveys DROP as_invite_template_id;
ALTER TABLE employmeo.account_surveys DROP as_static_link_view;
