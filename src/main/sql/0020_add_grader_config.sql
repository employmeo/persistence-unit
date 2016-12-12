CREATE TABLE employmeo.grader_configs
(
  grader_config_id bigserial NOT NULL,
  grader_config_asid bigint,
  grader_config_user_id bigint,
  grader_location_limitation_id bigint,
  grader_easyignore boolean,
  grader_summarize boolean,
  grader_notify boolean,
  CONSTRAINT grader_config_pkey PRIMARY KEY (grader_config_id),
  CONSTRAINT grader_config_grader_config_asid_fkey FOREIGN KEY (grader_config_asid)
      REFERENCES employmeo.account_surveys (as_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT grader_config_grader_user_id_fkey FOREIGN KEY (grader_config_user_id)
      REFERENCES employmeo.users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


ALTER TABLE employmeo.graders ADD grader_notes text;
ALTER TABLE employmeo.graders ADD grader_modified_date timestamp with time zone;

ALTER TABLE employmeo.responses ALTER COLUMN response_text TYPE text;
ALTER TABLE employmeo.grades ALTER COLUMN grade_text TYPE text;

--//@UNDO

ALTER TABLE employmeo.graders DROP grader_notes;
ALTER TABLE employmeo.graders DROP grader_modified_date;

ALTER TABLE employmeo.responses ALTER COLUMN response_text TYPE character varying(255);
ALTER TABLE employmeo.grades ALTER COLUMN grade_text TYPE character varying(255);

DROP TABLE employmeo.grader_configs;
