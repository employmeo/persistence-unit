
ALTER TABLE employmeo.corefactors ADD corefactor_color text;
ALTER TABLE employmeo.corefactors ADD corefactor_border text;
ALTER TABLE employmeo.corefactors ADD corefactor_default_coefficient double precision;
ALTER TABLE employmeo.corefactors ADD corefactor_created_date timestamp with time zone;
ALTER TABLE employmeo.corefactors ADD corefactor_modified_date timestamp with time zone;
ALTER TABLE employmeo.corefactor_descriptions ADD cfdesc_quartile integer;
ALTER TABLE employmeo.linear_regression_config ALTER COLUMN created_date DROP NOT NULL;

--//@UNDO

ALTER TABLE employmeo.linear_regression_config ALTER COLUMN created_date SET NOT NULL;
ALTER TABLE employmeo.corefactor_descriptions DROP cfdesc_quartile;
ALTER TABLE employmeo.corefactors DROP corefactor_modified_date;
ALTER TABLE employmeo.corefactors DROP corefactor_created_date;
ALTER TABLE employmeo.corefactors DROP corefactor_default_coefficient;
ALTER TABLE employmeo.corefactors DROP corefactor_border;
ALTER TABLE employmeo.corefactors DROP corefactor_color;
