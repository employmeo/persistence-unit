ALTER TABLE employmeo.accounts ADD account_stripe_id text;

--//@UNDO

ALTER TABLE employmeo.accounts DROP account_stripe_id;