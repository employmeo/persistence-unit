CREATE TABLE IF NOT EXISTS employmeo.subscription_plan
(
  subscription_plan_id bigserial NOT NULL PRIMARY KEY,
  plan_name varchar(50) NOT NULL,
  plan_reference varchar(50) NOT NULL,
  label varchar(50),
  description varchar(255),
  listable boolean NOT NULL DEFAULT true,
  term_value integer,
  term_unit varchar(10),
  total_cost double precision,
  installment_value double precision,
  num_installments integer,
  installment_frequency varchar(10),
  active_assessments_limit bigint default 0,
  scored_respondants_monthly_limit bigint  default 0,
  scored_respondants_total_limit bigint  default 0,
  total_benchmarks_limit bigint  default 0,  
  active                boolean NOT NULL DEFAULT true,
  created_date          timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uc_plan_reference UNIQUE ("plan_reference") 
)
WITH (
  OIDS=FALSE
);


INSERT INTO employmeo.subscription_plan (
	plan_name, plan_reference, label, description, listable, term_value, term_unit, total_cost, installment_value, num_installments, installment_frequency)
	VALUES ('free_trial', 'free_trial_v1', 'Free Trial', null, false, 30, 'D', 0.00, 0.00, 0, null);
INSERT INTO employmeo.subscription_plan (
	plan_name, plan_reference, label, description, listable, term_value, term_unit, total_cost, installment_value, num_installments, installment_frequency)
	VALUES ('unlimited_access', 'unlimited_access_internal_v1', 'Unrestricted - Internal', 'Unrestricted, full access', false, 10, 'Y', 0.00, 0.00, 0, null);


CREATE TABLE IF NOT EXISTS employmeo.account_subscription
(
  account_subscription_id bigserial NOT NULL PRIMARY KEY,
  account_id bigint NOT NULL,
  plan_id bigint NOT NULL,
  start_date date,
  end_date date,
  next_installment_date date,
  plan_status integer NOT NULL default 1,
  active_assessments_limit bigint,
  scored_respondants_monthly_limit bigint,
  scored_respondants_total_limit bigint,
  total_benchmarks_limit bigint,
  active                boolean NOT NULL DEFAULT true,
  created_date          timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_account_subscription_account_id
    FOREIGN KEY (account_id)
    REFERENCES employmeo.accounts(account_id),
  CONSTRAINT fk_account_subscription_plan_id
    FOREIGN KEY (plan_id)
    REFERENCES employmeo.subscription_plan(subscription_plan_id)
)
WITH (
  OIDS=FALSE
);  

--//@UNDO

DROP TABLE employmeo.account_subscription;
DROP TABLE employmeo.subscription_plan;
