
CREATE TABLE employmeo.scoring_scales
(
  scoring_scale_id bigserial NOT NULL,
  scale_a_low double precision NOT NULL,
  scale_b_low double precision NOT NULL,
  scale_c_low double precision NOT NULL,
  scale_name text,
  CONSTRAINT scoring_scales_pkey PRIMARY KEY (scoring_scale_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.custom_profiles
(
  custom_profile_id bigserial NOT NULL,
  custom_profile_a_name text,
  custom_profile_a_icon text,
  custom_profile_a_class text,
  custom_profile_b_name text,
  custom_profile_b_icon text,
  custom_profile_b_class text,
  custom_profile_c_name text,
  custom_profile_c_icon text,
  custom_profile_c_class text,
  custom_profile_d_name text,
  custom_profile_d_icon text,
  custom_profile_d_class text,
  CONSTRAINT custom_profiles_pkey PRIMARY KEY (custom_profile_id)
)
WITH (
  OIDS=FALSE
);


ALTER TABLE employmeo.accounts ADD account_custom_profile_id bigint;
ALTER TABLE employmeo.accounts ADD account_scoring_scale_id bigint;
ALTER TABLE employmeo.positions ADD position_scoring_scale_id bigint;

--//@UNDO

ALTER TABLE employmeo.accounts DROP account_custom_profile_id;
ALTER TABLE employmeo.accounts DROP account_scoring_scale_id;
ALTER TABLE employmeo.positions DROP position_scoring_scale_id;

DROP TABLE employmeo.scoring_scales;
DROP TABLE employmeo.custom_profiles;
