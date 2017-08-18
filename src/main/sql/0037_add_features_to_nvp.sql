ALTER TABLE employmeo.respondant_nvps ADD nvp_use_in_model boolean DEFAULT true;
ALTER TABLE employmeo.respondant_nvps ADD nvp_show_in_portal boolean DEFAULT false;
UPDATE employmeo.respondant_nvps set nvp_use_in_model = true, nvp_show_in_portal = false where nvp_use_in_model is null;
ALTER TABLE employmeo.questions ADD question_modified_date timestamp with time zone;
ALTER TABLE employmeo.questions ADD question_created_date timestamp with time zone;

CREATE TABLE employmeo.custom_workflows
(
  cw_id bigserial,
  cw_position_id bigint,
  cw_profile text,
  cw_trigger_point integer,
  cw_type text,
  cw_text text,
  cw_ats_id text,
  cw_notes text,
  cw_active boolean,
  cw_exec_order integer,
  CONSTRAINT custom_workflow_pkey PRIMARY KEY (cw_id),
  CONSTRAINT custom_workflow_cw_position_id_fkey FOREIGN KEY (cw_position_id)
      REFERENCES employmeo.positions (position_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.custom_workflows
  OWNER TO postgres;

--//@UNDO

DROP TABLE employmeo.custom_workflows;

ALTER TABLE employmeo.respondant_nvps DROP nvp_show_in_portal;
ALTER TABLE employmeo.respondant_nvps DROP nvp_use_in_model;
ALTER TABLE employmeo.questions DROP question_modified_date;
ALTER TABLE employmeo.questions DROP question_created_date;