ALTER TABLE employmeo.position_prediction_config ADD prediction_mean double precision;
ALTER TABLE employmeo.position_prediction_config ADD prediction_stdev double precision;
ALTER TABLE employmeo.position_prediction_config ADD prediction_pop_size bigint;

--//@UNDO

ALTER TABLE employmeo.position_prediction_config DROP prediction_pop_size;
ALTER TABLE employmeo.position_prediction_config DROP prediction_stdev;
ALTER TABLE employmeo.position_prediction_config DROP prediction_mean;
