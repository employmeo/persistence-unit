CREATE TABLE employmeo.reference_check_configs
(
  rcc_id bigserial NOT NULL,
  rcc_invite_template text,
  rcc_preamble text,
  rcc_min_references integer,
  rcc_redirect_page text,
  rcc_allow_anonymous boolean,
  CONSTRAINT reference_check_configs_pkey PRIMARY KEY (rcc_id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE employmeo.graders ADD COLUMN grader_config_id bigint;
ALTER TABLE employmeo.account_surveys ADD COLUMN as_grader_config_id bigint;

--//@UNDO
ALTER TABLE employmeo.account_surveys DROP COLUMN as_grader_config_id;
ALTER TABLE employmeo.graders DROP COLUMN grader_config_id;

DROP TABLE employmeo.reference_check_configs;