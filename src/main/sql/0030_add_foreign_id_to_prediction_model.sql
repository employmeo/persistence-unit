ALTER TABLE employmeo.prediction_models ADD model_foreign_id text;

--//@UNDO

ALTER TABLE employmeo.prediction_models DROP model_foreign_id;
