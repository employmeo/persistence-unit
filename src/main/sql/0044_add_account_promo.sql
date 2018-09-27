ALTER TABLE employmeo.accounts ADD account_promo text;
ALTER TABLE employmeo.surveys ADD survey_stripe_plan_id text;
ALTER TABLE employmeo.surveys ADD survey_default_preamble text;

--//@UNDO

ALTER TABLE employmeo.surveys DROP survey_default_preamble;
ALTER TABLE employmeo.surveys DROP survey_stripe_plan_id;
ALTER TABLE employmeo.accounts DROP account_promo;