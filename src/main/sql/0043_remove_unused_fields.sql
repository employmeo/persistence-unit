ALTER TABLE employmeo.accounts DROP account_feature_scoring;

ALTER TABLE employmeo.respondants DROP respondant_profile_a;
ALTER TABLE employmeo.respondants DROP respondant_profile_b;
ALTER TABLE employmeo.respondants DROP respondant_profile_c;
ALTER TABLE employmeo.respondants DROP respondant_profile_d;
ALTER TABLE employmeo.positions DROP position_target_tenure;
ALTER TABLE employmeo.positions DROP position_target_hireratio;
ALTER TABLE employmeo.users DROP user_avatar_url;
ALTER TABLE employmeo.users DROP user_locale;
ALTER TABLE employmeo.users DROP user_name;

DROP TABLE employmeo.dbdeploy_demo;
DROP TABLE employmeo.billing_item;
DROP TABLE employmeo.cf_migration_mapping_table;
DROP TABLE employmeo.payroll_entry;
DROP TABLE employmeo.payroll_summary;

--//@UNDO

ALTER TABLE employmeo.accounts ADD account_feature_scoring boolean;
ALTER TABLE employmeo.respondants ADD respondant_profile_a double precision;
ALTER TABLE employmeo.respondants ADD respondant_profile_b double precision;
ALTER TABLE employmeo.respondants ADD respondant_profile_c double precision;
ALTER TABLE employmeo.respondants ADD respondant_profile_d double precision;
ALTER TABLE employmeo.positions ADD COLUMN position_target_tenure numeric(10,0);
ALTER TABLE employmeo.positions ALTER COLUMN position_target_tenure SET DEFAULT NULL::numeric;
ALTER TABLE employmeo.positions ADD COLUMN position_target_hireratio numeric(10,0);
ALTER TABLE employmeo.positions ALTER COLUMN position_target_hireratio SET DEFAULT NULL::numeric;
ALTER TABLE employmeo.users ADD COLUMN user_avatar_url character varying(255);
ALTER TABLE employmeo.users ALTER COLUMN user_avatar_url SET DEFAULT NULL::character varying;
ALTER TABLE employmeo.users ADD COLUMN user_locale character varying(5);
ALTER TABLE employmeo.users ALTER COLUMN user_locale SET DEFAULT 'en_US'::character varying;
ALTER TABLE employmeo.users ADD COLUMN user_name character varying(255);
ALTER TABLE employmeo.users ALTER COLUMN user_name SET DEFAULT NULL::character varying;


CREATE TABLE employmeo.billing_item
(
  billing_item_id bigserial NOT NULL,
  billing_item_account_id bigint,
  billing_item_reference bigint,
  billing_item_date timestamp with time zone NOT NULL DEFAULT now(),
  billing_item_amount double precision,
  billing_item_description character varying(255),
  billing_item_status integer,
  CONSTRAINT pk_billing_item_id PRIMARY KEY (billing_item_id),
  CONSTRAINT fk_account_id FOREIGN KEY (billing_item_account_id)
      REFERENCES employmeo.accounts (account_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE employmeo.billing_item
  OWNER TO postgres;
CREATE TABLE employmeo.cf_migration_mapping_table
(
  cf_original_id bigint,
  cf_new_id bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.cf_migration_mapping_table
  OWNER TO postgres;
  
  CREATE TABLE employmeo.dbdeploy_demo
(
  id integer,
  data character varying(100)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.dbdeploy_demo
  OWNER TO postgres;
CREATE TABLE employmeo.payroll_entry
(
  payroll_entry_id bigserial NOT NULL,
  payroll_entry_person_id bigint NOT NULL,
  payroll_entry_period_start date NOT NULL,
  payroll_entry_period_end date,
  payroll_entry_total_hours numeric(10,0) DEFAULT (0)::numeric,
  payroll_entry_total_wages numeric(10,0) DEFAULT (0)::numeric,
  payroll_entry_paycode character varying(40),
  payroll_entry_paydate date,
  payroll_entry_category character varying(40),
  CONSTRAINT payroll_entry_pkey PRIMARY KEY (payroll_entry_id),
  CONSTRAINT fk_payroll_entry_person_id FOREIGN KEY (payroll_entry_person_id)
      REFERENCES employmeo.persons (person_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.payroll_entry
  OWNER TO postgres;

CREATE INDEX fki_payroll_entry_person_id
  ON employmeo.payroll_entry
  USING btree
  (payroll_entry_person_id);


CREATE TABLE employmeo.payroll_summary
(
  payroll_person_id bigint NOT NULL,
  payroll_hire_date date,
  payroll_start_date date,
  payroll_end_date date,
  payroll_still_employed boolean,
  payroll_total_hours numeric(10,0) DEFAULT NULL::numeric,
  payroll_total_wages numeric(10,0) DEFAULT NULL::numeric,
  payroll_start_wage_rate numeric(10,0) DEFAULT NULL::numeric,
  payroll_latest_wage_rate numeric(10,0) DEFAULT NULL::numeric,
  payroll_weekly_hours numeric(10,0) DEFAULT NULL::numeric,
  payroll_bonus_wages numeric(10,0) DEFAULT NULL::numeric,
  CONSTRAINT payroll_summary_pkey PRIMARY KEY (payroll_person_id),
  CONSTRAINT fk_payroll_summary_person_id FOREIGN KEY (payroll_person_id)
      REFERENCES employmeo.persons (person_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.payroll_summary
  OWNER TO postgres;
