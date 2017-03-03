CREATE TABLE employmeo.nvp_names
(
  nvp_name_id bigserial NOT NULL,
  nvp_name text,
  CONSTRAINT nvp_names_pkey PRIMARY KEY (nvp_name_id),
  CONSTRAINT nvp_names_nvp_name_key UNIQUE (nvp_name)
)
WITH (
  OIDS=FALSE
);

INSERT INTO employmeo.nvp_names (nvp_name) SELECT DISTINCT nvp_name from employmeo.respondant_nvps;
ALTER TABLE employmeo.respondant_nvps ADD nvp_name_id bigint;
UPDATE employmeo.respondant_nvps AS r SET nvp_name_id = i.nvp_name_id FROM employmeo.nvp_names i WHERE r.nvp_name = i.nvp_name;

ALTER TABLE employmeo.position_prediction_config ADD display_priority integer;
ALTER TABLE employmeo.position_prediction_config ADD prediction_trigger_point integer;
ALTER TABLE employmeo.predictions ADD prediction_foreign_id text;
ALTER TABLE employmeo.predictions ADD prediction_value boolean;
ALTER TABLE employmeo.predictions ADD prediction_target_id bigint;
ALTER TABLE employmeo.prediction_models ADD prediction_model_prep text;
ALTER TABLE employmeo.prediction_models ADD prediction_model_prep_name text;

--//@UNDO

ALTER TABLE employmeo.respondant_nvps DROP nvp_name_id;
ALTER TABLE employmeo.predictions DROP prediction_foreign_id;
ALTER TABLE employmeo.predictions DROP prediction_value;
ALTER TABLE employmeo.predictions DROP prediction_target_id;
ALTER TABLE employmeo.prediction_models DROP prediction_model_prep;
ALTER TABLE employmeo.prediction_models DROP prediction_model_prep_name;
ALTER TABLE employmeo.position_prediction_config DROP display_priority;
ALTER TABLE employmeo.position_prediction_config DROP prediction_trigger_point;

DROP TABLE employmeo.nvp_names;