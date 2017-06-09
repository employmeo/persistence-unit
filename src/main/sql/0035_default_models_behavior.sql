
ALTER TABLE employmeo.surveys ADD survey_default_models text;
ALTER TABLE employmeo.surveys ADD survey_modified_date timestamp with time zone;
ALTER TABLE employmeo.prediction_models ADD default_mean double precision;
ALTER TABLE employmeo.prediction_models ADD default_stdev double precision;
ALTER TABLE employmeo.prediction_models ADD default_popsize bigint;

--//@UNDO

ALTER TABLE employmeo.surveys DROP survey_default_models;
ALTER TABLE employmeo.surveys DROP survey_modified_date;
ALTER TABLE employmeo.prediction_models DROP default_mean;
ALTER TABLE employmeo.prediction_models DROP default_stdev;
ALTER TABLE employmeo.prediction_models DROP default_popsize;