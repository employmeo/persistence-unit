ALTER TABLE employmeo.corefactors ALTER corefactor_id type bigint;
ALTER TABLE employmeo.linear_regression_config ALTER corefactor_id type bigint;

--//@UNDO

ALTER TABLE employmeo.linear_regression_config ALTER corefactor_id type integer;
ALTER TABLE employmeo.corefactors ALTER corefactor_id type integer;